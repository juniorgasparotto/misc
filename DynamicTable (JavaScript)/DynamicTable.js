	/*
		Por: Glaber Gasparotto Junior
		Data: 2007-11-15
		Desc: Cria uma tabela apartir de javascript apartir de um datasource
	*/
	function DynamicTable(Name) {
		this.Columns 			  = Array();	
		this.DataSource 		  = null;
		this.DataSourceType		  = 'property' // 'array';
		this.HasHeader 		  	  = true;
		this.Name 		  	  	  = Name ? Name : "DynamicTable1";
		this.ChooseDataSource 	  = false;
		this.CharColumnEmpty	  = "&nbsp;";
		this.oColumns 			  = Array();
		this.TagHeader		  	  = "th";
		
		var InitLine		  	  = -1 		// Inicia com header;
		var InitColumn		  	  = 0 		// Inicia com header;
		var IndexLineHeader		  = -1;
		
		
		this.Configurations =  function() {
			
		}
		
		CopyColumn = function(Column) {
			return {
						Id				 : Column.Id,
						ValueDefault	 : Column.ValueDefault,
						DataSourceColumn : Column.DataSourceColumn,
						ColumnName		 : Column.ColumnName
					}
		}
		
		this.ChangePosition = function (From,To) {
			var Column = CopyColumn(this.Columns[From]);
			
			this.Columns[From] = this.Columns[To];
			this.Columns[To]   = Column;
		}
		
		this.AddColumn = function (Column,ChangeIndex) {
			if (!(ChangeIndex==null)) {
				this.AddColumn (Column, null);
				this.ChangePosition (this.Columns.length-1,ChangeIndex);
			}
			else {
				this.Columns[this.Columns.length] = Column;
			}
			this.CountColumn++;
		}
		
		this.GetColumn = function (Index) {
			return this.Columns[Index];
		}
		
		this.EditColumn = function (Column,Index) {
			this.Columns[Index] = Column
		}
	
		this.RemoveColumn = function (Index) {
			this.Columns[Index]["Deleted"] = true;
			
			//Reduz de 1 o numero de colunas
			this.CountColumn--;
		}
		
		this.IsColumnActive = function(Index) {
			if (this.Columns[Index]["Deleted"] == true)
				return false;
	
			return true;
		}
		
		this.print_r  = function (Obj) {
				var s = " array(";
				for (a in Obj) {
					if (a.length>0) {
						for (b in a) {
							s += "<br>" + a;
						}
					}
					else {
						s += "<br>" + a;
					}
				}
				s += ")";
				return s;
		}
	
		this.InitializeDataSourceArray = function() {
			this.CountDataSourceLine = this.DataSource.length;
			
			if(this.CountDataSourceLine>0) {
				this.CountDataSourceColumn = this.DataSource[0].length;
			}
		}
	
		this.InitializeDataSourceProperty = function() {
			var MaxLenDs = 0;
			
			if (this.DataSources!=null) {
				var LenDs 			  = this.DataSources.length;
				this.ChooseDataSource = true;
				
				//Descobri qual dos DataSource tem mais linhas
				for (var i=0; i<LenDs; i++) {				
					if (MaxLenDs<this.DataSources[i].length) {
						MaxLenDs = this.DataSources[i].length;
					}
				}
			}
			else if (this.DataSource!=null) {
				MaxLenDs = this.DataSource.length;
			}
			
			this.CountDataSourceLine 	= MaxLenDs
			this.CountDataSourceColumn  = this.Columns.length;
		}
	
		this.Init  = function() {
			
			//Diz que a classe já foi inicializada
			this.IsInitiate			= true;			
			this.CharColumnEmpty 	= "&nbsp;";		
			this.Table  			= this.Table 	? this.Table : document.createElement("table");
			this.Header 			= this.Header 	? this.Header : document.createElement("thead");
			this.Body 				= this.Body 	? this.Body : document.createElement("tbody");
			this.Footer 			= this.Footer 	? this.Footer : document.createElement("tfoot");
			
			if (this.HasHeader)
				InitLine = -1;
			else
				InitLine = 0;
			
				
			if (this.HasHeader) this.Table.appendChild(this.Header);
			this.Table.appendChild(this.Body);
			
			//Inicializa data source
			this.InitializeDataSource();
		}
		
		this.InitializeDataSource = function() {
			switch (this.DataSourceType) {
				case "property":
					this.InitializeDataSourceProperty();
					break;
				case "array":
					this.InitializeDataSourceArray();
					break;
			}
		}
		
		this.getDataSourceValueArray = function (Line,Column) {
			return this.DataSource[Line][Column];
		}
	
		//Recupera o nome da fonte de valor da propriedade
		this.getColumnSourceValueProperty = function (Column) {
			return this.Columns[Column]['DataSourceColumn'];
		}
		
		this.getCurrentDataLine = function(Line,Column,DataLine) {
			var line;
			try {
				//Se a fonte for mult-datasource
				if (Column!=null) {
					//Verifica qual é a fonte de dados da coluna
					if (this.Columns[Column]["DataSource"]==null)
						line = null;
					else  {
						//Verifica se o datasource da coluna vira do parametro ou do array
						if (DataLine==null)
							line = this.DataSources[this.Columns[Column]['DataSource']][Line];
						else {
							line = DataLine[this.Columns[Column]['DataSource']];
						}
					}
				}
				else if (!this.ChooseDataSource)
					line = this.DataSource[Line];
			}
			catch(ex) {}
			
			return line;
		}
	
		this.getDataSourceValueProperty = function (Column,ValueToPropertyColumn) {
			var value = '';			
			var SourceValue = this.getColumnSourceValueProperty(Column);
			
			try {
				if (ValueToPropertyColumn && this.Columns[Column][ValueToPropertyColumn]) {
					value = this.Columns[Column][ValueToPropertyColumn];
				}	
				else {
					if (this.Columns[Column]['FreeEvaluate'])
							value = eval(SourceValue);
						else
							value = eval("this.CurrentDataLine." + SourceValue);
				}
			}
			catch(ex) {}
		
			if (typeof value=='function') {
				value = value(this);
			}
			
			if (!value) value = this.CharColumnEmpty;
			
			return value
		}
	
		this.getDataSourceValue = function (Column,ValueToPropertyColumn) {
			switch (this.DataSourceType) {
				case "property":
					return this.getDataSourceValueProperty(Column,ValueToPropertyColumn);
					break;
				case "array":
					return this.getDataSourceValueArray(Column,ValueToPropertyColumn);
					break;
			}
		}
		
		ExecFunction = function (Index) {
			try {
				if (this.Columns[Index]["ExecFunction"]) {
					this.Columns[Index]["ExecFunction"](this);
				}
			}
			catch(e) {}
		}
		
		this.getTagCreateColumn = function () {
			switch (this.CurrentIndexLine) 
			{
				case -1:
					if (this.HasHeader) {
						return "th"
						break;
					}
				default:
					return "td"
					break;	
			} 
		}
		
		this.Append = function (Parent) {
			Parent.appendChild(this.Table);
		}
		
		this.AddHeader	 = function(DataLine,ChooseDataSource) {
			if (!this.IsInitiate) this.Init();
			
			//Se for multdatasources e nenhum valor para o parametro ChooseDataSource for passado, assume true
			if (ChooseDataSource==null && this.ChooseDataSource) ChooseDataSource=true;

			var row = this.__AddLine__(DataLine,this.Header,this.TagHeader,"ColumnName",ChooseDataSource);
			if (row) {
				this.Header.appendChild(row);
			}
		}
		
		this.AddLine	 = function(DataLine, ChooseDataSource) {
			// Se a classe não estiver iniciada e precisar de cabeçalho o cria
			if (this.HasHeader && !this.IsInitiate) {
				this.Init();
				this.AddHeader(DataLine,ChooseDataSource);	
			}
			else {
				if (!this.IsInitiate) this.Init();
			}
			
			//Se for multdatasources e nenhum valor para o parametro ChooseDataSource for passado, assume true
			if (ChooseDataSource==null && this.ChooseDataSource) ChooseDataSource=true;			
			
			var row = this.__AddLine__(DataLine,this.Body,"","",ChooseDataSource);
			if (row) {
				this.CountLine++;
				this.Body.appendChild(row);
			}	
		}
		
		this.__AddLine__ = function (DataLine, Parent, TypeLine, ValueToPropertyColumn, ChooseDataSource) {			
			this.Row = document.createElement("tr");

			for ( this.CurrentIndexColumn = InitColumn; this.CurrentIndexColumn < this.CountDataSourceColumn; this.CurrentIndexColumn++ ) {
				if (!this.IsColumnActive(this.CurrentIndexColumn)) continue;
				
				//Quando a coluna escolhe de qual datasource vira a linha, do processo Bind() DataLine vem null
				// Caso seja o processo de AddLine() o DataLine vem com um linha, caso seja MultDataSorce Entra no If
				// do contrato segue o fluxo com um unico datasource
				
				if (ChooseDataSource) {
					_DataLine = this.getCurrentDataLine(this.CurrentIndexLine,this.CurrentIndexColumn,DataLine);					
				}
				else 
					_DataLine = DataLine;

				//Seta a linha corrente	
				this.CurrentDataLine = _DataLine;
				
				// Cria elemento <td>
				this.Cell = document.createElement(TypeLine?TypeLine:"td");
				//var cellText = document.createHtmlNode(this.getDataSourceValue(this.CurrentIndexLine,this.CurrentIndexColumn));
				//cell.appendChild(cellText);
				
				this.Cell.innerHTML = this.getDataSourceValue(this.CurrentIndexColumn,ValueToPropertyColumn);
				
				//Antes de inserir a coluna
				if (this.Events && this.Events.BeforeAddColumn)
					this.Cell = this.Events.BeforeAddColumn(this)===false ? false : this.Cell;
	
				if (this.Cell)
					this.Row.appendChild(this.Cell);
					
				//Depois de inserir a coluna
				if (this.Events && this.Events.AfterAddColumn)
					this.Events.AfterAddColumn(this,this.Cell===false ? false : true);
					
				//Faz uma execução	
				ExecFunction(this.CurrentIndexColumn);				
			}
			
			//Antes de inserir a linha
			if (this.Events && this.Events.BeforeAddingRow)
				this.Row = this.Events.BeforeAddingRow(this)===false ? false : this.Row;
			
			if (this.Row)
				Parent.appendChild(this.Row);
			
			//Depois de inserir a linha
			if (this.Events && this.Events.AfterAddingRow)
				this.Events.AfterAddingRow(this,this.Row===false ? false : true);

			return this.Row;
		}
		
		this.Bind = function() {
			//Inicializa classe
			if(!this.IsInitiate) this.Init();
	
			for (this.CurrentIndexLine = InitLine; this.CurrentIndexLine < this.CountDataSourceLine; this.CurrentIndexLine++) {
				var row;
				if (this.CurrentIndexLine==IndexLineHeader) {
					this.AddHeader(this.getCurrentDataLine(this.CurrentIndexLine), this.ChooseDataSource);
				}
				else {
					//Retorna a linha do source atual e adiciona, caso seja multi-datasource, a coluna escolhe qual será.
					this.AddLine(this.getCurrentDataLine(this.CurrentIndexLine), this.ChooseDataSource);
				}
			}
		}
	}
	
	function DynamicTableColumn () {
		this.Id 				= "";
		this.ValueDefault 		= "";
		this.DataSourceColumn  	= "";
		this.ColumnName 	  	= "";
		this.FreeEvaluate 	  	= "";
		this.DataSource 	  	= "";
	}
		
