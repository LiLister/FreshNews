# FreshNews

## Features

* Both the news sources and headlines are fetched from https://newsapi.org;
* List the sources first. If user clicked one source, fetch headlines on that source;

## implementation

* The api to fetch sources: https://newsapi.org/docs/endpoints/sources
* The api to fetch headlines: https://newsapi.org/docs/endpoints/top-headline

For sources, no additional parameter need be passed. 

For headlines, we could use "sources" to specify the source, "page" and "pageSize" to fetch the headlines with 
pagination support. With "totalResults" to check whether there are more pages available.

