package com.sri.photoapp.feignproxy;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 400:
			break;

		case 404: {
			if (methodKey.contains("userAlbums")) {

				return new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Users Albums are not found from feign,check endpoints syntax and semantix and url");
			}
			break;
		}

		default:
			return new Exception(response.reason());

		}
		return null;
	}

}
