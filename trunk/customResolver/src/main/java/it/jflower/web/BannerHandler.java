package it.jflower.web;

import it.jflower.par.Banner;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@ConversationScoped
@Named
public class BannerHandler implements Serializable {

	private Banner banner;

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}
}
