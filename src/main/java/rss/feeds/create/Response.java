package rss.feeds.create;

import java.util.List;

public class Response {
	private String title;

	public String getTitle() {
		return title;
	}

	public Response setTitle(String title) {
		this.title = title;
		return this;
	}

	private String link;

	public String getLink() {
		return link;
	}

	public Response setLink(String link) {
		this.link = link;
		return this;
	}

	private String uri;

	public String getUri() {
		return uri;
	}

	public Response setUri(String uri) {
		this.uri = uri;
		return this;
	}

	private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public Response setArticles(List<Article> articles) {
		this.articles = articles;
		return this;
	}

	@Override
	public String toString() {
		return "Response [title=" + title + ", link=" + link + ", uri=" + uri + ", articles=" + articles + "]";
	}

	public static class Article {
		private String title;

		public String getTitle() {
			return title;
		}

		public Article setTitle(String title) {
			this.title = title;
			return this;
		}

		private String link;

		public String getLink() {
			return link;
		}

		public Article setLink(String link) {
			this.link = link;
			return this;
		}

		private String description;

		public String getDescription() {
			return description;
		}

		public Article setDescription(String uri) {
			this.description = uri;
			return this;
		}

		@Override
		public String toString() {
			return "Article [title=" + title + ", link=" + link + ", description=" + description + "]";
		}

	}
}
