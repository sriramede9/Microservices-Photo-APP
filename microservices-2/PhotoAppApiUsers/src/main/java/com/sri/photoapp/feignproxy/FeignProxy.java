package com.sri.photoapp.feignproxy;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sri.photoapp.ui.model.AlbumResponseModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "ALBUMS-WS",fallback = AlbumsFallBack.class)
//@FeignClient(name = "ALBUMS-WS", fallback = AlbumsFallBackFactory.class)
public interface FeignProxy {

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE
//	                MediaType.APPLICATION_XML_VALUE,
	}, path = "/users/{id}/albums")
	public List<AlbumResponseModel> userAlbums(@PathVariable String id);
}

//@Component
//class AlbumsFallBackFactory implements FallbackFactory<FeignProxy> {
//
//	@Override
//	public FeignProxy create(Throwable cause) {
//		// write some logic to handle feign exception
//		// the idea is to log exception and then send fallback response
//
//		return new AlbumsFallBack();
//	}
//
//}

@Component
class AlbumsFallBack implements FeignProxy {

	// if the feign proxy is down [Albums-ws] then this fallback method is called

//	private Throwable cause=new Throwable();

	

	private Logger log =LoggerFactory.getLogger(AlbumsFallBack.class);

//	public AlbumsFallBack(Throwable cause) {
//		// TODO Auto-generated constructor stub
//		this.cause = cause;
//	}

	public AlbumsFallBack() {
//		this.cause = new Throwable();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<AlbumResponseModel> userAlbums(String id) {

		// if clause to check the cause if of type feignException

//		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
//
//			log.error("404 error when get albmuns service is called with user id \t" + id);
//		} else {
//			log.error("other exception took place \t" + cause.getLocalizedMessage());
//		}

		return Arrays.asList(new AlbumResponseModel("feign-fallback", id, "fallback-name", "fall-back response"));
	}

}