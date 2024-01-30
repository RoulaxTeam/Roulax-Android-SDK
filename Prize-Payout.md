# **Prize Payout**

In order to ensure the security of in-app reward issuance, developers who require server verification need to set up a server address to complete verification with the RSDK server.

## **Server-to-server payout**

If the reward sending verification is turned on, our server will make a request to the callback address you filled in, and we will determine whether the reward is really issued based on the returned result. For details on the request parameters and return parameter format, please refer to the following document:

## **Endpoint structureEndpoint structure**

We will call your endpoint with an `HTTP GET` request and the configured parameters:

| Parameter    | Detail                                                       | Format    |
| :----------- | :----------------------------------------------------------- | :-------- |
| sign         | sign                                                         | mandatory |
| send_time    | Send time                                                    | mandatory |
| {user_id}    | The Id of the user that should get the rewards               | mandatory |
| {prize_id}   | The Prize ID，the in-app prize referred to are determined by negotiation | mandatory |
| {trans_id}   | The unique transaction ID                                    | mandatory |
| {gaid}       | gaid                                                         | optional  |
| {mac}        | mac                                                          | optional  |
| {imei}       | imei                                                         | optional  |
| {android_id} | android_id                                                   | optional  |

## **Example Endpoint URL**

```
https://example.com/example?[YourParamName]={user_id}&[YourParamName]={prize_id}&[YourParamName]={trans_id}&[YourParamName]={gaid}&[YourParamName]={mac}&[YourParamName]={imei}&[YourParamName]={android_id}
```

## ** About sign generation **
Get all parameters except sign from the url request, sort all parameters in ascending order of ASCII code, concatenate their corresponding parameter values in string format, and concatenate the value of the pub key at the end of the string. , and finally perform 32-bit lowercase md5 encryption on the spliced string, and the obtained value is the value of the corresponding sign parameter in the URL request.

sign generation example
```
Assume the pub key is h  (The pub key parameter value is obtained from the Account module of the background management page.)

http://example.com/example?user_id=raop&trans_id=2024012416101025896&gaid=7D3802B7-FFA4-4BE5-8F49-633F76536C35&mb=020000000000&ma=351962088678293&mc=e395fcf7baab8cde&token=0EZDkVuWFqV4nu2v&prize_id=A123&send_time=1706083810&sign=62ca5744026fd45c0cb15ed719d94e2e

1.Get all parameters (except the sign parameter) according to the url, sort them in ascending order of ASCII codes, and then concatenate their corresponding values in string format. The corresponding value is: 1defc1694588072ba
2.Splice the pub key value at the end : 1defc1694588072bah
3.After performing 32-bit lowercase md5 encryption on the spliced string, the corresponding value is obtained: 62ca5744026fd45c0cb15ed719d94e2e. This 62ca5744026fd45c0cb15ed719d94e2e is equal to the value of the request parameter sign in the URL.


```
## **Request to return results**
```
{
    "is_valid": true
}

The request return result must be returned in json format. The return parameter must contain is_valid. When is_valid is true, it means success, and when it is false, it means failure.
```