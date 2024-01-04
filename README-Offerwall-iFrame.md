# **Offerwall iFrame 接入文档**

The Roulax Offerwall API provides a solution for publishers who want to include an offerwall in their application/website but have control over the presentation of the offers. All offers matching the parameters provided will be returned and can be displayed to the user.

## **获取你的接入链接**

1.创建你的应用，App Join Type并选择H5

![offerwall-iframe-app](./img/offerwall-iframe-app.png)

2.创建你的Unit

![offerwall-iframe-unit](D:\magicstudio\project\Roulax-Android-SDK\img\offerwall-iframe-unit.png)

3、创建完成，你可以获取到你的Offerwall集成链接

![offewall-iframe-img](D:\magicstudio\project\Roulax-Android-SDK\img\offewall-iframe-img.png)

## JavaScript

```
window.open("https://wall.roulax.io/discovery?app_id={app_id}&unit_id={unit_id}&publisher_id={pulisher_id}&gaid={gaid}&userid={userid}")
```

## iFrame

```
<iframe src="https://wall.roulax.io/discovery?app_id={appid}&unit_id={unitid}&publisher_id={pulisher_id}&gaid={gaid}&userid={userid}"></iframe>
```

| Parameters   | Description                                                  | Format | Sample    |
| ------------ | ------------------------------------------------------------ | ------ | --------- |
| app_id       | 你的AppId，可从平台中获取。（从平台中获取的OW H5 Link已填写app_id） | String | mandatory |
| unit_id      | 你的UnitId，可从平台中获取。（从平台中获取的OW H5 Link已填写unit_id） | String | mandatory |
| publisher_id | 你的PublisherId，可从平台中获取。（从平台中获取的OW H5 Link已填写publisher_id） | String | mandatory |
| gaid         | 如果是Android用户，请填写GAID；如果是iOS用户，请填写IDFA     | String | mandatory |
| userid       | 唯一的用户ID                                                 | String | mandatory |