# **OwReport**
| :-----------  | :------------------------------------------------- |
| request url   | https://pub-api.roulax.io                          |
| url path      | /api/external/ow_report                            |
| request method| GET                                                |

## **Endpoint structureEndpoint structure**

We will call your endpoint with an `HTTP GET` request and the configured parameters:

| Parameter    | Detail                                         | type     |example                           | Format    |
| :----------- | :----------------------------------------------| :--------| :--------                        | :-------- |
| sign         | sign                                           | string   | 95c5b7b27107c11f6200217e6aec0782 | mandatory |
| sign_t       | sign time  (time stamp)                        | int      | 1694588072                       | mandatory |
| app_id       | app id    (Supports multiple, separated by English commas when multiple) | int      | 1,2                       | mandatory |
| start_date   | query start time  (Timestamp format, query range from the current day to the previous 31 days)   | int      | 1694588072                    | mandatory |
| end_date     | query end time    (Timestamp format, query range from the current day to the previous 31 days)   | int      | 1694588072                    | mandatory |
| unit_id      | unit id    (Supports multiple, separated by English commas when multiple) | string      | 1,2                       | optional |
| ad_type_id   | unit Advertising slot type (Supports multiple, separated by English commas when multiple) | string      | 1,2                       | optional |
| country_code | country code (Supports multiple, separated by English commas when multiple) | string      | UA,UK                       | optional |
| group_on     | data dimension grouping (Supported grouping dimensions:date,app_id,unit_id,ad_type_id,country_code . If this parameter is not passed, the date dimension will be used for grouping by default.) | string      | UA,UK                       | optional |
| page | current page number (Default 1) | int      | 1                       | optional |
| page_size | number of pages (Default 50,max 200) | int      | 50                       | optional |


## **Example Endpoint URL**

```
https://pub-api.roulax.io/api/external/ow_report?start_date=20231101&end_date=20231130&area=IN,US&unit_id=305&app_id=75&unit_type=14,15,16,17&sign=35ae439558823907b1e8206ed3fae36a&sign_t=1694588072&group_on=date,app_id,unit_id
```

## ** About sign generation **
Get all parameters except sign from the url request, sort all parameters in ascending order of ASCII code, concatenate their corresponding parameter values in string format, and concatenate the value of the pub key at the end of the string. , and finally perform 32-bit lowercase md5 encryption on the spliced string, and the obtained value is the value of the corresponding sign parameter in the URL request.

sign generation example
```
Assume the pub key is h  (The pub key parameter value is obtained from the Account module of the background management page.)

start_date=20231101&end_date=20231130&area=IN,US&unit_id=305&app_id=75&unit_type=14,15,16,17&sign_t=1694588072&group_on=date,app_id,unit_id

1.Get all parameters (except the sign parameter) according to the url, sort them in ascending order of ASCII codes, and then concatenate their corresponding values in string format. The corresponding value is: 75IN,US20231130date,app_id,unit_id16945880722023110130514,15,16,17
2.Splice the pub key value at the end : 75IN,US20231130date,app_id,unit_id16945880722023110130514,15,16,17h
3.After performing 32-bit lowercase md5 encryption on the spliced string, the corresponding value is obtained: 9a77ac15eddbd164dcfa874d0f5e5dc0. This 9a77ac15eddbd164dcfa874d0f5e5dc0 is the value of sign


```


## **return parameters**

| Parameter    | Detail                                         | type     |example      | Format    |
| :----------- | :----------------------------------------------| :--------| :--------   | :-------- |
| status         | status                                       |int       | 200 | mandatory |
| msg         | message                                         |string   | success | mandatory |
| data         | data                                           |object   |  | mandatory |
| data.page         | page                                      | int   | 1 | mandatory |
| data.page_size         | page_size                            | int   | 50 | mandatory |
| data.total         | total                                    | int   | 2 | mandatory |
| data.list          | data list                                | array |  | mandatory |
| data.list..impression| date                                   | int   | 1 | mandatory |
| data.list..earning    | date                                  | float   | 0.01 | mandatory |
| data.list..ecpm    | date                                     | float   | 0.01 | mandatory |
| data.list..date    | date                                     | int   | 20240301 | optional |
| data.list..app_id  | data list                                | int   | 1 | optional |
| data.list..unit_id  | unit id                                 | int   | 1 | optional |
| data.list..ad_type_id  | unit Advertising slot type           | int   | 14 | optional |
| data.list..country_code  | country code                       | string   | US | optional |


## **Return status description**

| status code   | detail                           |
| :-----------  | :------------------------------------------------- |
| 200   | success                       |
| 1000  | sign error                            |
| 1001  | ThThe request limit for the day has been reached. The current limit is 30 times per day. |
| 2000  | Operation error |
| 2001  | The request parameters are incorrect |
| 2002  | The app_id associated is incorrect |

## **Successfully returned case**

```
{"status":200,"msg":"success","data":{"page":1,"page_size":50,"total":2,"list":[{"impression":0,"earning":0,"date":20231118,"app_id":75,"unit_id":305,"country_code":"IN","ad_type_id":15,"ecpm":0},{"impression":9,"earning":0.51,"date":20231118,"app_id":75,"unit_id":305,"country_code":"US","ad_type_id":15,"ecpm":56.66667}]}}
```

## **Failed returned case**

```
{"status":1000,"msg":"sign error","data":{}}
```


## **The parameters of group_on return the association **
Supported grouping dimensions:date,app_id,unit_id,ad_type_id,country_code . If this parameter is not passed, the date dimension will be used for grouping by default.
group_on After the grouping dimension parameter of the query exists, the returned parameter will also increase the corresponding parameter value dimension return

```
example : group_on value is empty , the date dimension will be used for grouping by default

{"status":200,"msg":"success","data":{"page":1,"page_size":50,"total":1,"list":[{"impression":9,"earning":0.51,"date":20231118,"ecpm":56.66667}]}}


example : The value of group_on includes app_id,unit_id
{"status":200,"msg":"success","data":{"page":1,"page_size":50,"total":1,"list":[{"impression":9,"earning":0.51,"app_id":75,"unit_id":305,"ecpm":56.66667}]}}

example : The value of group_on includes app_id,unit_id,country_code
{"status":200,"msg":"success","data":{"page":1,"page_size":50,"total":2,"list":[{"impression":0,"earning":0,"app_id":75,"unit_id":305,"country_code":"IN","ecpm":0},{"impression":9,"earning":0.51,"app_id":75,"unit_id":305,"country_code":"US","ecpm":56.66667}]}}

``` 