    <meta name="inneractive-cid" content="17051722021" />
        <meta name="inneractive-session" content="1442007347" />
    <meta name="inneractive-error" content="OK"/>
    <meta name="inneractive-ad-width" content="728" />
    <meta name="inneractive-ad-height" content="90" />
    <meta name="inneractive-ad-network" content="MdotM_RTB" />
    <meta name="inneractive-ad-type" content="4"/>

	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="HandheldFriendly" content="true" />
	<meta name="MobileOptimized" content="width" />

	<script language="javascript" src="http://cdn2.inner-active.mobi/wv-js/iagetters.js"></script>
<script>
</script>
 	<script src="mraid.js"></script>


<link rel="stylesheet" type="text/css" href="http://cdn2.inner-active.mobi/wv-css/151211/b_style.css" />
<div id="append"></div>

      <img id='iaIImpressionTrackingPixel' alt="" style='width:0px;height:0px;border:0px;margin:0px;float:left;'/>

      <script>
          document.getElementById('iaIImpressionTrackingPixel').src = "http://event.inner-active.mobi/simpleM2M/impressionTrackPixel?redirect=false&sessionId=1442007347&cid=17051722021&aid=27748&v=2.1.0-iPad-2.0.1.7&po=947&cip=109.190.97.174&ua=Mozilla%2F5.0+%28iPad%3B+CPU+OS+7_0_2+like+Mac+OS+X%29+AppleWebKit%2F537.51.1+%28KHTML%2C+like+Gecko%29+Mobile%2F11A501&re=9&l=0&cuid=17019101379";
      </script>


	<a href="${AD_A_HREF}">
        <img src='${AD_IMG_SRC}' width='728' height='90' border='0' /></a>
	<script>
	        	function addTrackingPixel(trackingPixelSrc, shouldSendArguments, argumentsObj) {
	        		if (trackingPixelSrc != '') {
	        			var track = new Image();
	                    if (shouldSendArguments) {
	                        track.src = trackingPixelSrc + encodeURI(JSON.stringify(argumentsObj));
	                    } else {
	                        track.src = trackingPixelSrc;
	                    }
	        			track.width = '1';
	        			track.height = '1';

	        			document.getElementById("track_href").appendChild(track);
	        	   }
	        	}

	        	function redirect(hrefObj, argumentsObj) {
	        		var timeout = setTimeout(function(){
	        						mraid.open(hrefObj.href);
	        	   }, 400);
	        	}

	        	function addTrackingPixelForClick(hrefObj) {
	        			// This will be done inside the ormma/mraid open
	        	}

	        	function reportOpen(hrefObj) {
	        		try {
	        			addTrackingPixelForClick(hrefObj);
	        		} catch(err) {
	        			// does nothing TODO
	        		}

	        		if (oldOnClick) {
	        			oldOnClick.apply();
	        		}
	        		redirect(hrefObj);
	        		return false;
	        	}

	        	function redirectToAdPage(adPageUrl){
	        		top.location.href = adPageUrl;
	        	}

	        	function attachHrefHandler(hrefObj) {
	        	   if(hrefObj.id && hrefObj.id.substring(0,2) == 'ia') {
	        			return false;
	        	   }
	        	   if (hrefObj.href.substring(0,10) == 'javascript') {
	        			return false;
	        	   }

	        	   oldOnClick = hrefObj.onclick;
	        	   hrefObj.onclick=function(){
	        			reportOpen(hrefObj, oldOnClick);

	        			return false;
	        		}
	        	}

	            /*
	        	var linksArray = document.getElementsByTagName('a');	
	                for(var i = 0 ; i < linksArray.length ; i++){
	                    var currentLink = linksArray[i];
	                    attachHrefHandler(currentLink);
	                }
	        	*/

	    			/*
	    			this function triggers when a href is clicked
	    			the function will
	    			- take the original url of the href
	    			- build another url that points to IA server
	    			- add the original url as a parameter that can be accessed in IA server and use the redirect url from the server
	    			*/
	    			function linkClicked(link, event){
	    				//url to ia server.  you can change to a real remote server
	    				var iaServer = "http://wv.inner-active.mobi/simpleM2M/redirect/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/?pcp=&acp=";
	    				var linkHref = link.href;

	    				// don't change the link twice, if the user already clicked on the ad once, but didn't navigate (such as in double tap on the ad).
	    				if (linkHref.indexOf(iaServer + "&url=") == -1) {
	    				    var originalURL = encodeURIComponent(linkHref);
	                        var iaURL = iaServer;
	                        iaURL += "&url=" + originalURL;
	                        link.href = iaURL;
	    				}
	                }

	    			var elements = document.getElementsByTagName('a');
	    			var iaClientUpdateStatus = "http://wv.inner-active.mobi/simpleM2M/ClientUpdateStatus?cn=ZingMagic_ZingMagic_FialV_iOS_iPhone&amp;v=2_1_0-iPad-2_0_1_7&amp;ci=17051722021&amp;s=1442007347&amp;cip=109-190-97-174&amp;po=947&amp;re=9&amp;lt=0&amp;cc=FR&amp;acp=&amp;pcp=".replace(/&amp;/g, "&");
	    			var iaRedirect = "http://wv.inner-active.mobi/simpleM2M/redirect/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/?pcp=&acp=".replace(/&amp;/g, "&");
	    			for(var i = 0, len = elements.length; i < len; i++) {
	    				var currentElement = elements[i];
	    				if (!(currentElement.href.indexOf(iaRedirect) == 0)
	    				        && !(currentElement.href.indexOf(iaClientUpdateStatus) == 0)
	    				        && (currentElement.href.indexOf("http") == 0)) {
	                        if (currentElement.addEventListener){  // W3C DOM
	                            currentElement.addEventListener("click", function(event) {
	                                            linkClicked(this, event);
	                            });
	                        } else if (currentElement.attachEvent) { // IE DOM
	                            currentElement.attachEvent("onclick", function(event) {
	                                            linkClicked(currentElement, event);
	                            });
	                        } else { // No much to do

	                        }
	    				}
	    			}
	</script>
	<div id="track_href"></div>

    <div id="iaRichMediaBridge"></div>
	<script>
			      var iaBridge = "" +
			      "mraidExpand = mraid.expand;" +
			      "mraidClose = mraid.close;" +
			      "mraidOpen = mraid.open;" +
			      "mraidResize = mraid.resize;" +
			      "mraidCreateCalendarEvent = mraid.createCalendarEvent;" +
			      "mraidStorePicture = mraid.storePicture;" +
			      "mraidPlayVideo = mraid.playVideo;";


			      iaBridge += "" +
			      "var IA_CLCK = \"http://wv.inner-active.mobi/simpleM2M/rich/4/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/?pcp=&acp=\";" +
			      "var AFF_CLCK = \"\";" +
			      "var PUB_CLCK = \"\";" +
			      "var ADV_CLCK = \"\";" +
			      "var IA_CLSE = \"http://wv.inner-active.mobi/simpleM2M/rich/2/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/\";" +
			      "var IA_EXPND = \"http://wv.inner-active.mobi/simpleM2M/rich/1/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/\";" +
			      "var IA_RSZE = \"http://wv.inner-active.mobi/simpleM2M/rich/26/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/\";" +
			      "var IA_CLDR = \"http://wv.inner-active.mobi/simpleM2M/rich/25/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/\";" +
			      "var IA_STPI = \"http://wv.inner-active.mobi/simpleM2M/rich/27/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/\";" +
			      "var IA_PLVD = \"http://wv.inner-active.mobi/simpleM2M/rich/28/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/\";" +

			      "var iaHandleTrackingPixel = function(action) {" +
			      "       var trackingImage = addTrackingPixel(IA_EXPND, true, arguments);" +
			      "       try {   " +
			      "           trackingImage.onload = action;" +
			      "           trackingImage.onerror = action;" +
			      "       } catch(e) {" +
			      "           action();" +
			      "       }"+
			      "};" +

			      "mraid.expand = function(URL) {" +
			      "       var action = function() { mraidExpand.apply(this, arguments); };" +
			      "       iaHandleTrackingPixel(action);" +
			      "};" +
			      "mraid.close = function() {" +
			      "       addTrackingPixel(IA_CLSE, true, arguments);" +
			      "       mraidClose.apply(this, arguments);" +
			      "};" +
			      "mraid.resize = function() {" +
			      "       addTrackingPixel(IA_RSZE, true, arguments);" +
			      "       mraidResize.apply(this, arguments);" +
			      "};" +
			      "mraid.createCalendarEvent = function() {" +
			      "       addTrackingPixel(IA_CLDR, true, arguments);" +
			      "       mraidCreateCalendarEvent.apply(this, arguments);" +
			      "};" +
			      "mraid.storePicture = function() {" +
			      "       addTrackingPixel(IA_STPI, true, arguments);" +
			      "       mraidStorePicture.apply(this, arguments);" +
			      "};" +
			      "mraid.playVideo = function() {" +
			      "       addTrackingPixel(IA_PLVD, true, arguments);" +
			      "       mraidPlayVideo.apply(this, arguments);" +
			      "};" +
			      "mraid.open = function(URL){" +
			      "addTrackingPixel(IA_CLCK, true, arguments);"
			      //+ "addTrackingPixel(AFF_CLCK, false, arguments);"
			      //+ "addTrackingPixel(PUB_CLCK, false, arguments);"
			      //+ "addTrackingPixel(ADV_CLCK, false, arguments);"
			      + "mraidOpen.apply(this, arguments);" +
			      "};";

			      var IA_ERROR = "http://wv.inner-active.mobi/simpleM2M/rich/13/1442007347/27748/17051722021/947/9/2_1_0-iPad-2_0_1_7/0/";

			      function iaHandleErrorEvent(message, action) {addTrackingPixel(IA_ERROR, true, arguments);}

			      function iaHandleStateChangeEvent(state) {
			      	var videosArray = document.getElementsByTagName('video');
			      	for(var i = 0 ; i < videosArray.length ; i++){
			      		    videosArray[i].pause();
			      	}
			      }

			      if (typeof mraid !== 'undefined' && typeof mraid.addEventListener === 'function') {
			          mraid.addEventListener( 'error', iaHandleErrorEvent );
			          /*mraid.addEventListener( 'ready', iaHandleReadyEvent );*/
			          mraid.addEventListener( 'stateChange', iaHandleStateChangeEvent );
			          /*mraid.addEventListener( 'viewableChange', iaHandleViewableChangeEvent );*/

			          var myscript = document.createElement('script');
			          myscript.innerHTML = iaBridge;
			          document.getElementById("iaRichMediaBridge").appendChild(myscript);
			      }



	</script>
