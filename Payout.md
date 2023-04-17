# **Payout**

In order for the user to redeem his collected rewards, they must be paid out. A reward can only be paid out exactly once.In order for the user to redeem his collected rewards, they must be paid out. A reward can only be paid out exactly once.

## **Server-to-server payout**

In order to use the server-to-server payout, you need to create an endpoint on your server that we can request in order to notify you of the user's rewards. Once you receive the notification, it will be up to you to deliver it to the user.

## **Endpoint structureEndpoint structure**

We will call your endpoint with an `HTTP GET` request and the configured parameters:

| Parameter    | Detail                                             | Format    |
| :----------- | :------------------------------------------------- | :-------- |
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