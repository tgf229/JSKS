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
//执行请求
export function netClient(object,query) {
		fetch(query)
			.then(response => response.json())
			.then(json => object.busCB(json))
			.catch(error => 
				object.setState({
					isLoading:false,
				})
			);
}