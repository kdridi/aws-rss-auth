package rss.feeds.create;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	public static Response feedToJson(final String location) {
		try {
			final SyndFeedInput feedInput = new SyndFeedInput();
			final SyndFeed feed = feedInput.build(new XmlReader(new URL(location)));

			final List<?> feedEntries = feed.getEntries();

			final Response responseBody = new Response().setTitle(feed.getTitleEx().getValue()).setLink(feed.getLink())
					.setUri(feed.getUri()).setArticles(feedEntries.stream().map(e -> SyndEntry.class.cast(e)).map(e -> {
						return new Response.Article().setTitle(e.getTitleEx().getValue()).setLink(e.getLink())
								.setDescription(e.getDescription() == null ? null : e.getDescription().getValue());
					}).collect(Collectors.toList()));

			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);

		final String body = String.class.cast(input.get("body"));

		final Gson g = new Gson();
		final HandlerBody handlerBody = g.fromJson(body, HandlerBody.class);

		return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(Handler.feedToJson(handlerBody.location))
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
	}
}
