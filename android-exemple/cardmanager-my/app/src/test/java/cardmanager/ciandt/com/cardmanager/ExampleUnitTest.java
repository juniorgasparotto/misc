package cardmanager.ciandt.com.cardmanager;

import org.junit.Test;

import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.WebApiRepository;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationResult;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testLogin() throws Exception {
        WebApiRepository repository = new WebApiRepository("http://cardmanagerserver.herokuapp.com/");
        OperationResult<User> userInvalid  = repository.login(null, null);

        assertTrue(userInvalid.type == OperationResult.ResultType.ERROR);
        assertTrue(userInvalid.result == null);
        assertTrue(userInvalid.error.code == OperationError.ERROR_CODE_SERVER_WITH_MESSAGE);
        assertTrue(userInvalid.error.message.equals("Usuário ou senha incorretos!"));

        OperationResult<User> user  = repository.login("wp@gmail.com", "123456");

        assertTrue(user.type == OperationResult.ResultType.SUCCESS);
        assertTrue(user.result != null);
        assertTrue(user.error == null);
    }

    @Test
    public void testSignUp() throws Exception {
        WebApiRepository repository = new WebApiRepository("http://cardmanagerserver.herokuapp.com/");
        OperationResult<Boolean> result  = repository.signUp("name", "e-mail", "992590679", "123456789");

        assertTrue(result.type == OperationResult.ResultType.SUCCESS);
        assertTrue(result.result);
        assertTrue(result.error == null);

//        assertTrue(userInvalid.type == OperationResult.ResultType.ERROR);
//        assertTrue(userInvalid.result == null);
//        assertTrue(userInvalid.error.code == OperationError.ERROR_CODE_SERVER_WITH_MESSAGE);
//        assertTrue(userInvalid.error.message.equals("Usuário ou senha incorretos!"));


    }
}