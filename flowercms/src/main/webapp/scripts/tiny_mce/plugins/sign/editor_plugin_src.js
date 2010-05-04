/*
 * 	Sign Plugin for TinyMCE version 3
 */


(function() {
	// Load plugin specific language pack
	tinymce.PluginManager.requireLangPack('sign');

	tinymce.create('tinymce.plugins.SignPlugin', {
		/**
		 * Initializes the plugin, this will be executed after the plugin has been created.
		 * This call is done before the editor instance has finished it's initialization so use the onInit event
		 * of the editor instance to intercept that event.
		 *
		 * @param {tinymce.Editor} ed Editor instance that the plugin is initialized in.
		 * @param {string} url Absolute URL to where the plugin is located.
		 */
		init : function(ed, url) {
			// Register the command so that it can be invoked by using tinyMCE.activeEditor.execCommand('mceExample');
			ed.addCommand('mceSign', function() {
				ed.windowManager.open({
					file : url + '/image.jsf',
					width : 480 + parseInt(ed.getLang('example.delta_width', 0)),
					height : 400 + parseInt(ed.getLang('example.delta_height', 0)),
					inline : 1
				}, {
					plugin_url : url, // Plugin absolute URL
					some_custom_arg : 'custom arg' // Custom argument
				});
			});

			// Register sign button
			ed.addButton('sign', {
				title : 'Inserisci firma',
				cmd : 'mceSign',
				image : url + '/images/sign.gif'
			});

			// Add a node change handler, selects the button in the UI when a image is selected
			ed.onNodeChange.add(function(ed, cm, n) {
				if (n == null)
					return;

				do {
					if (n.nodeName == "IMG" && tinyMCE.getAttrib(node, 'class').indexOf('mceItem') == -1) {
						tinyMCE.switchClass(ed + '_advimage', 'mceButtonSelected');
						return true;
					}
				} while ((n = n.parentNode));

				cm.setActive('_advimage', false); 

				return true;
			});
		},

		/**
		 * Creates control instances based in the incomming name. This method is normally not
		 * needed since the addButton method of the tinymce.Editor class is a more easy way of adding buttons
		 * but you sometimes need to create more complex controls like listboxes, split buttons etc then this
		 * method can be used to create those.
		 *
		 * @param {String} n Name of the control to create.
		 * @param {tinymce.ControlManager} cm Control manager to use inorder to create new control.
		 * @return {tinymce.ui.Control} New control instance or null if no control was created.
		 */
		createControl : function(n, cm) {
			return null;
		},

		/**
		 * Returns information about the plugin as a name/value array.
		 * The current keys are longname, author, authorurl, infourl and version.
		 *
		 * @return {Object} Name/value array containing information about the plugin.
		 */
		getInfo : function() {
			return {
				longname : 'Sign plugin',
				author : 'Fulvio Di Marco',
				authorurl : '',
				infourl : '',
				version : "1.0"
			};
		},
		
		cleanup : function(type, content) {
			switch (type) {
				case "insert_to_editor_dom":
					var imgs = content.getElementsByTagName("img");
					for (var i=0; i<imgs.length; i++) {
						var onmouseover = tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i], 'onmouseover'));
						var onmouseout = tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i], 'onmouseout'));

						if ((src = this._getImageSrc(onmouseover)) != "") {
							if (tinyMCE.getParam('convert_urls'))
								src = tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings['base_href'], src);

							imgs[i].setAttribute('onmouseover', "this.src='" + src + "';");
						}

						if ((src = this._getImageSrc(onmouseout)) != "") {
							if (tinyMCE.getParam('convert_urls'))
								src = tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings['base_href'], src);

							imgs[i].setAttribute('onmouseout', "this.src='" + src + "';");
						}
					}
					break;

				case "get_from_editor_dom":
					var imgs = content.getElementsByTagName("img");
					for (var i=0; i<imgs.length; i++) {
						var onmouseover = tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i], 'onmouseover'));
						var onmouseout = tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i], 'onmouseout'));

						if ((src = this._getImageSrc(onmouseover)) != "") {
							if (tinyMCE.getParam('convert_urls'))
								src = eval(tinyMCE.settings['urlconverter_callback'] + "(src, null, true);");

							imgs[i].setAttribute('onmouseover', "this.src='" + src + "';");
						}

						if ((src = this._getImageSrc(onmouseout)) != "") {
							if (tinyMCE.getParam('convert_urls'))
								src = eval(tinyMCE.settings['urlconverter_callback'] + "(src, null, true);");

							imgs[i].setAttribute('onmouseout', "this.src='" + src + "';");
						}
					}
					break;
			}

			return content;
		},
		
		/**
		 * Returns the image src from a scripted mouse over image str.
		 *
		 * @param {string} s String to get real src from.
		 * @return Image src from a scripted mouse over image str.
		 * @type string
		 */
		_getImageSrc : function(s) {
			var sr, p = -1;

			if (!s)
				return "";

			if ((p = s.indexOf('this.src=')) != -1) {
				sr = s.substring(p + 10);
				sr = sr.substring(0, sr.indexOf('\''));

				return sr;
			}

			return "";
		}
		
	});

	// Register plugin
	tinymce.PluginManager.add('sign', tinymce.plugins.SignPlugin);
})();