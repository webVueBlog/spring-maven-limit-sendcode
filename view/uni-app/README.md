## uni-app接入
### 1. 兼容性
	微信小程序,H5页面等(uni-app支持的小程序系系列,H5系列)
### 2. 初始化组件
	1)将view/uni-app/src/pages/verify文件夹copy到自己项目中

	2)下载相关依赖, npm install crypto-js -S

#### 基础示例

```javascript
<template>
	<Verify
		@success="'success'" //验证成功的回调函数
		:mode="'pop'"     //调用的模式
		:captchaType="'blockPuzzle'"    //调用的类型 点选或者滑动   
		:imgSize="{ width: '330px', height: '155px' }" //图片的大小对象
		ref="verify"
	></Verify>
	//mode="pop"模式
    <button @click="useVerify">调用验证组件</button>
</template>

****注: mode为"pop"时,使用组件需要给组件添加ref值,并且手动调用show方法 例: this.$refs.verify.show()**** 
****注: mode为"fixed"时,无需添加ref值,无需调用show()方法****

<script>
//引入组件
import Verify from "./../../components/verifition/Verify";
export default {
	name: 'app',
	components: {
		Verify
	}
	methods:{
		success(params){
		  // params 返回的二次验证参数, 和登录参数一起回传给登录接口，方便后台进行二次验证
        },
        useVerify(){
            this.$refs.verify.show()
        }
	}
}
</script>
</script>
```
### 注意事项
	后端请求地址根据部署情况到:\view\uni-app\src\pages\verify\utils\request.js  第二行 修改路劲
```javascript
let baseUrl = "https://captcha./captcha-api"
```

### 3.回调事件

|  参数 | 类型 |  说明 |
| ------------ | ------------ | ------------ |
| success(params)  |  funciton | 验证码匹配成功后的回调函数,params为返回需回传服务器的二次验证参数  |
| error  |  funciton | 验证码匹配失败后的回调函数  |
| ready  |  funciton |  验证码初始化成功的回调函数 |

### 4. 验证码参数

|  参数 | 类型 |  说明 |
| ------------ | ------------ | ------------ |
| captchaType | String | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord  |
| mode  | String | 验证码的显示方式，弹出式pop，固定fixed，默认：mode : ‘pop’  |
| vSpace  | String | 验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，默认:vSpace:5  |
| explain  | String |  滑动条内的提示，不设置默认是：'向右滑动完成验证' |
| imgSize | Object |  其中包含了width、height两个参数，分别代表图片的宽度和高度，支持百分比方式设置 如:{width:'400px',height:'200px'}

### 5.1默认接口api地址
|  请求URL | 请求方式 |  
| ------------ | ------------ |
| /captcha/get  | Post | 
| /captcha/check  | Post | 

### 5.2 获取验证码接口详情
#### 接口地址：http://*:*/captcha/get
##### 请求参数：
```json
{
	"captchaType": "blockPuzzle"  //验证码类型 clickWord
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "originalImageBase64": "底图base64",
        "point": {    //默认不返回的，校验的就是该坐标信息，允许误差范围
            "x": 205,
            "y": 5
        },
        "jigsawImageBase64": "滑块图base64",
        "token": "71dd26999e314f9abb0c635336976635", //一次校验唯一标识
        "result": false,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```
### 5.3 核对验证码接口详情
#### 请求接口：http://*:*/captcha/check
##### 请求参数：
```json
{
	 "captchaType": "blockPuzzle",
	 "pointJson": "QxIVdlJoWUi04iM+65hTow==",  //aes加密坐标信息
	 "token": "71dd26999e314f9abb0c635336976635"  //get请求返回的token
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "captchaType": "blockPuzzle",
        "token": "71dd26999e314f9abb0c635336976635",
        "result": true,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```
