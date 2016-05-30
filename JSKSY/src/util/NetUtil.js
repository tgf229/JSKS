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
const URL_ADDR = "http://58.213.145.35/web-mobile/";
// const URL_ADDR = "http://10.2.48.16:9080/web-mobile/";
// const URL_ADDR = "http://172.16.1.8:8080/web-mobile/";

export const BUS_100101 = "Bus100101";
export const BUS_100201 = "Bus100201";
export const BUS_100301 = "Bus100301";
export const BUS_100401 = "Bus100401";

export const BUS_200101 = "Bus200101";
export const BUS_200201 = "Bus200201";

export const BUS_400101 = "Bus400101";
export const BUS_400201 = "Bus400201";

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
			.then(response => response.json())
			.then(json => busCB(object,json))
			.catch(error => 
				console.log('error='+error)
				// object.setState({
					//isLoading:false,
				// })
			);
}  