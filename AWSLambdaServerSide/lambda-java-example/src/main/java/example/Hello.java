package example;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class Hello {
	public static class NameInfo {
		private String firstName;
	    private String lastName;

	    public NameInfo() {}

	    public NameInfo(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }

	    public String getFirstName() {
	        return firstName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }
	}

    public static String javaTestFunction(NameInfo input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("First name : " + input.getFirstName());
        logger.log("Last name: " + input.getLastName());
        input.setFirstName("asdf");
        return "First name: " + input.getFirstName() + " Last name: " + input.getLastName();
    }
}