package com.kaleido;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.util.Collections;

/**
 * This class allows the ID generator to be packaged as an AWS Lambda function that responds to API Gateway calls
 */
public class IdGeneratorHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final IdGenerator idGenerator = new IdGenerator();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request,
                                                      Context context) {

        String bodyJson = request.getBody();
        Input input = gson.fromJson(bodyJson, Input.class);

        String id = idGenerator.convertFromNumber(input.getNumber(), input.getPadStrings(), input.getCodices());

        return new APIGatewayProxyResponseEvent().withBody(id)
                                                 .withHeaders(Collections.emptyMap())
                                                 .withStatusCode(200);
    }
}
