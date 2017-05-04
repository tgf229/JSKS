//封装url
export function urlForQueryAndPage(key,value,pageNum) {
	var data = {
		country:'uk',
		pretty:'1',
		encoding:'json',
		listing_type:'buy',
		action:'search_listings',
		page:pageNum,
	};
	data[key] = value;
	var queryString = Object.keys(data).map(key=> key+'='+encodeURIComponent(data[key])).join('&');
	return 'http://api.nestoria.co.uk/api?'+queryString;
}

// export const URL_ADDR = "https://app.jseea.cn/";
// export const URL_ADDR = "http://58.213.145.35/";
export const URL_ADDR = "http://192.168.0.102:8888/";
// export const URL_ADDR = "http://192.168.1.104:8888/web-mobile/";
// export const URL_ADDR = "http://172.16.1.8:8080/web-mobile/";

export const BUS_100101 = "Bus100101";
export const BUS_100201 = "Bus100201";
export const BUS_100301 = "Bus100301";
export const BUS_100401 = "Bus100401";
export const BUS_100501 = "Bus100501";

export const BUS_200101 = "Bus200101";
export const BUS_200201 = "Bus200201";

export const BUS_300101 = "Bus300101";
export const BUS_300201 = "Bus300201";

export const BUS_400101 = "Bus400101";
export const BUS_400201 = "Bus400201";

export const BUS_600101 = "Bus600101";

export const BUS_700101 = "Bus700101";
export const BUS_700201 = "Bus700201";
export const BUS_700301 = "Bus700301";
export const BUS_700401 = "Bus700401";
export const BUS_700501 = "Bus700501";
export const REQ_TIPS = "正在拼命查询中，请稍候...";
export const ERROR_TIPS = "未查询到任何信息...";

export const YEAR_2017 = '2017';  
export const YEAR_2016 = '2016';
export const YEAR_2015 = '2015';
export const YEAR_2014 = '2014';
export const YEAR_2013 = '2013';

//执行请求
export function netClient(object,query) {
		fetch(query)
			.then(response => response.json())
			.then(json => object.busCB(json))
			.catch(error => 
				object.setState({
					//isLoading:false,
				})
			);
}

export function netClientPostEncrypt(object,busName,busCB,params) {
        var data = Object.keys(params).map(key=> key+'='+encodeURIComponent(params[key])).join('&');
        fetch(URL_ADDR+busName,{
                    method:'POST',
                    body: data
            })
            .then(response => busCB(object,response))

            .catch(error => 
                console.log('error='+error)
                // object.setState({
                    //isLoading:false,
                // })
            );
}  

export function netClientPost(object,busName,busCB,params) {
		var data = Object.keys(params).map(key=> key+'='+encodeURIComponent(params[key])).join('&');
		fetch(URL_ADDR+busName,{
					method:'POST',
					body: data
			})
			.then(response => {
				console.log(response);
				return response.json();
			})
			.then(json => busCB(object,json))
			.catch(error => 
				console.log('error='+error)
				// object.setState({
					//isLoading:false,
				// })
			);
} 

export function netClientTest(object,busName,busCB,params){
	var data = Object.keys(params).map(key=> key+'='+encodeURIComponent(params[key])).join('&');
	fetch("http://192.168.0.107/jszk/"+busName+".json")
	.then(response => {
		return response.json();
	})
	.then(json => busCB(object,json))
	.catch(error => busCB(object,error));

}