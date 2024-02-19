# **Payout**

In order for the user to redeem his collected rewards, they must be paid out. A reward can only be paid out exactly once.In order for the user to redeem his collected rewards, they must be paid out. A reward can only be paid out exactly once.

## **Server-to-server payout**

In order to use the server-to-server payout, you need to create an endpoint on your server that we can request in order to notify you of the user's rewards. Once you receive the notification, it will be up to you to deliver it to the user.

## **Endpoint structureEndpoint structure**

We will call your endpoint with an `HTTP GET` request and the configured parameters:

| Parameter    | Detail                                             | Format    |
| :----------- | :------------------------------------------------- | :-------- |
| sign         | sign                                               | mandatory |
| send_time    | Send time                                          | mandatory |
| {user_id}    | The Id of the user that should get the rewards     | mandatory |
| {amount}     | The amount of virtual currency the user should get | mandatory |
| {trans_id}   | The unique transaction ID                          | mandatory |
| {gaid}       | gaid                                               | optional  |
| {mac}        | mac                                                | optional  |
| {imei}       | imei                                               | optional  |
| {android_id} | android_id                                         | optional  |

## **Example Endpoint URL**

```
https://example.com/example?[YourParamName]={user_id}&[YourParamName]={amount}&[YourParamName]={trans_id}&[YourParamName]={gaid}&[YourParamName]={mac}&[YourParamName]={imei}&[YourParamName]={android_id}
```

## ** About sign generation **
Get all parameters except sign from the url request, sort all parameters in ascending order of ASCII code, concatenate their corresponding parameter values in string format, and concatenate the value of the pub key at the end of the string. , and finally perform 32-bit lowercase md5 encryption on the spliced string, and the obtained value is the value of the corresponding sign parameter in the URL request.

sign generation example
```
Assume the pub key is h  (The pub key parameter value is obtained from the Account module of the background management page.)

http://example.com/example?user_id=a&amount=1&trans_id=b&gaid=e&mac=c&imei=f&android_id=d&send_time=1694588072&sign=95c5b7b27107c11f6200217e6aec0782

1.Get all parameters (except the sign parameter) according to the url, sort them in ascending order of ASCII codes, and then concatenate their corresponding values in string format. The corresponding value is: 1defc1694588072ba
2.Splice the pub key value at the end : 1defc1694588072bah
3.After performing 32-bit lowercase md5 encryption on the spliced string, the corresponding value is obtained: 95c5b7b27107c11f6200217e6aec0782. This 95c5b7b27107c11f6200217e6aec0782 is equal to the value of the request parameter sign in the URL.


```