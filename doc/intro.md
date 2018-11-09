# Introduction to bestmixer

BestMixer.io is a popular multicoin mixing service, currently it supports bitcoin, bitcoin cash, litecoin and soon ethereum.

You can register for an API key after which you can use this library to interface with the service, for example you can allow users to use BestMixer via your website.

First you need to set your api-key

```
(api-key "api-key")
```

Then you can issue API calls such as

```
(get-fee-info)
```

See the API spec [here](https://bestmixer.io/en/api).