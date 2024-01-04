# **Offerwall iFrame Integration Guide**

The Roulax Offerwall API provides a solution for publishers who want to include an offerwall in their application/website but have control over the presentation of the offers. All offers matching the parameters provided will be returned and can be displayed to the user.

## **Get your access link**

1.Create your app, App Join Type and select H5

![offerwall-iframe-app](./img/offerwall-iframe-app.png)

2.Create your Unit

![offerwall-iframe-unit](./img/offerwall-iframe-unit.png)

3、After the creation is completed, you can get your Offerwall integration link

![offewall-iframe-img](./img/offewall-iframe-img.png)

## JavaScript

```
window.open("https://wall.roulax.io/discovery?app_id={app_id}&unit_id={unit_id}&publisher_id={pulisher_id}&gaid={gaid}&userid={userid}")
```

## iFrame

```
<iframe src="https://wall.roulax.io/discovery?app_id={appid}&unit_id={unitid}&publisher_id={pulisher_id}&gaid={gaid}&userid={userid}"></iframe>
```

| Parameters   | Description                                                  | Format    |
| ------------ | ------------------------------------------------------------ | --------- |
| app_id       | Your AppId can be obtained from the platform. (The OW H5 Link obtained from the platform has filled in app_id) | mandatory |
| unit_id      | Your UnitId can be obtained from the platform. (The OW H5 Link obtained from the platform has filled in unit_id) | mandatory |
| publisher_id | Your PublisherId can be obtained from the platform. (The OW H5 Link obtained from the platform has filled in publisher_id) | mandatory |
| gaid         | If you are an Android user, please fill in GAID; if you are an iOS user, please fill in IDFA | mandatory |
| userid       | Unique client's user ID                                      | mandatory |