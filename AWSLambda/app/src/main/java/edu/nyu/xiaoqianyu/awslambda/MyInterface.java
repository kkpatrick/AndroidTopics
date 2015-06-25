package edu.nyu.xiaoqianyu.awslambda;

/**
 * Created by abc on 6/25/15.
 */
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface MyInterface {
    @LambdaFunction
    String echo(NameInfo nameInfo);

    /**
     * Invoke lambda function "echo". The functionName in the annotation
     * overrides the default which is the method name
     */
    @LambdaFunction(functionName = "echo")
    void noEcho(NameInfo nameInfo);

    @LambdaFunction(functionName = "javaTestFunction")
    String javaTestFunction(NameInfo nameInfo);
}
