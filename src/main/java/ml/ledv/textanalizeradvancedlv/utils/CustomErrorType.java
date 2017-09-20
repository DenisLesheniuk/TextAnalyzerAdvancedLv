package ml.ledv.textanalizeradvancedlv.utils;

/***
 * Class for creating error names
 *
 * @author Denis Lesheniuk
 * @version 1.0
 * */
public class CustomErrorType {
    private String errorMessage;

    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
