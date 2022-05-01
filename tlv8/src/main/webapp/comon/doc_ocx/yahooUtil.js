/*
 * file from "UI\system\service\doc\transport\yahooDomEvent.js"
 */

/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 2.8.2r1
*/
if(typeof YAHOO=="undefined"||!YAHOO){var YAHOO={};}YAHOO.namespace=function(){var A=arguments,E=null,C,B,D;for(C=0;C<A.length;C=C+1){D=(""+A[C]).split(".");E=YAHOO;for(B=(D[0]=="YAHOO")?1:0;B<D.length;B=B+1){E[D[B]]=E[D[B]]||{};E=E[D[B]];}}return E;};YAHOO.log=function(D,A,C){var B=YAHOO.widget.Logger;if(B&&B.log){return B.log(D,A,C);}else{return false;}};YAHOO.register=function(A,E,D){var I=YAHOO.env.modules,B,H,G,F,C;if(!I[A]){I[A]={versions:[],builds:[]};}B=I[A];H=D.version;G=D.build;F=YAHOO.env.listeners;B.name=A;B.version=H;B.build=G;B.versions.push(H);B.builds.push(G);B.mainClass=E;for(C=0;C<F.length;C=C+1){F[C](B);}if(E){E.VERSION=H;E.BUILD=G;}else{YAHOO.log("mainClass is undefined for module "+A,"warn");}};YAHOO.env=YAHOO.env||{modules:[],listeners:[]};YAHOO.env.getVersion=function(A){return YAHOO.env.modules[A]||null;};YAHOO.env.ua=function(){var D=function(H){var I=0;return parseFloat(H.replace(/\./g,function(){return(I++==1)?"":".";}));},G=navigator,F={ie:0,opera:0,gecko:0,webkit:0,mobile:null,air:0,caja:G.cajaVersion,secure:false,os:null},C=navigator&&navigator.userAgent,E=window&&window.location,B=E&&E.href,A;F.secure=B&&(B.toLowerCase().indexOf("https")===0);if(C){if((/windows|win32/i).test(C)){F.os="windows";}else{if((/macintosh/i).test(C)){F.os="macintosh";}}if((/KHTML/).test(C)){F.webkit=1;}A=C.match(/AppleWebKit\/([^\s]*)/);if(A&&A[1]){F.webkit=D(A[1]);if(/ Mobile\//.test(C)){F.mobile="Apple";}else{A=C.match(/NokiaN[^\/]*/);if(A){F.mobile=A[0];}}A=C.match(/AdobeAIR\/([^\s]*)/);if(A){F.air=A[0];}}if(!F.webkit){A=C.match(/Opera[\s\/]([^\s]*)/);if(A&&A[1]){F.opera=D(A[1]);A=C.match(/Opera Mini[^;]*/);if(A){F.mobile=A[0];}}else{A=C.match(/MSIE\s([^;]*)/);if(A&&A[1]){F.ie=D(A[1]);}else{A=C.match(/Gecko\/([^\s]*)/);if(A){F.gecko=1;A=C.match(/rv:([^\s\)]*)/);if(A&&A[1]){F.gecko=D(A[1]);}}}}}}return F;}();(function(){YAHOO.namespace("util","widget","example");if("undefined"!==typeof YAHOO_config){var B=YAHOO_config.listener,A=YAHOO.env.listeners,D=true,C;if(B){for(C=0;C<A.length;C++){if(A[C]==B){D=false;break;}}if(D){A.push(B);}}}})();YAHOO.lang=YAHOO.lang||{};(function(){var B=YAHOO.lang,A=Object.prototype,H="[object Array]",C="[object Function]",G="[object Object]",E=[],F=["toString","valueOf"],D={isArray:function(I){return A.toString.apply(I)===H;},isBoolean:function(I){return typeof I==="boolean";},isFunction:function(I){return(typeof I==="function")||A.toString.apply(I)===C;},isNull:function(I){return I===null;},isNumber:function(I){return typeof I==="number"&&isFinite(I);},isObject:function(I){return(I&&(typeof I==="object"||B.isFunction(I)))||false;},isString:function(I){return typeof I==="string";},isUndefined:function(I){return typeof I==="undefined";},_IEEnumFix:(YAHOO.env.ua.ie)?function(K,J){var I,M,L;for(I=0;I<F.length;I=I+1){M=F[I];L=J[M];if(B.isFunction(L)&&L!=A[M]){K[M]=L;}}}:function(){},extend:function(L,M,K){if(!M||!L){throw new Error("extend failed, please check that "+"all dependencies are included.");}var J=function(){},I;J.prototype=M.prototype;L.prototype=new J();L.prototype.constructor=L;L.superclass=M.prototype;if(M.prototype.constructor==A.constructor){M.prototype.constructor=M;}if(K){for(I in K){if(B.hasOwnProperty(K,I)){L.prototype[I]=K[I];}}B._IEEnumFix(L.prototype,K);}},augmentObject:function(M,L){if(!L||!M){throw new Error("Absorb failed, verify dependencies.");}var I=arguments,K,N,J=I[2];if(J&&J!==true){for(K=2;K<I.length;K=K+1){M[I[K]]=L[I[K]];}}else{for(N in L){if(J||!(N in M)){M[N]=L[N];}}B._IEEnumFix(M,L);}},augmentProto:function(L,K){if(!K||!L){throw new Error("Augment failed, verify dependencies.");}var I=[L.prototype,K.prototype],J;for(J=2;J<arguments.length;J=J+1){I.push(arguments[J]);}B.augmentObject.apply(this,I);},dump:function(I,N){var K,M,P=[],Q="{...}",J="f(){...}",O=", ",L=" => ";if(!B.isObject(I)){return I+"";}else{if(I instanceof Date||("nodeType" in I&&"tagName" in I)){return I;}else{if(B.isFunction(I)){return J;}}}N=(B.isNumber(N))?N:3;if(B.isArray(I)){P.push("[");for(K=0,M=I.length;K<M;K=K+1){if(B.isObject(I[K])){P.push((N>0)?B.dump(I[K],N-1):Q);}else{P.push(I[K]);}P.push(O);}if(P.length>1){P.pop();}P.push("]");}else{P.push("{");for(K in I){if(B.hasOwnProperty(I,K)){P.push(K+L);if(B.isObject(I[K])){P.push((N>0)?B.dump(I[K],N-1):Q);}else{P.push(I[K]);}P.push(O);}}if(P.length>1){P.pop();}P.push("}");}return P.join("");},substitute:function(Y,J,R){var N,M,L,U,V,X,T=[],K,O="dump",S=" ",I="{",W="}",Q,P;for(;;){N=Y.lastIndexOf(I);if(N<0){break;}M=Y.indexOf(W,N);if(N+1>=M){break;}K=Y.substring(N+1,M);U=K;X=null;L=U.indexOf(S);if(L>-1){X=U.substring(L+1);U=U.substring(0,L);}V=J[U];if(R){V=R(U,V,X);}if(B.isObject(V)){if(B.isArray(V)){V=B.dump(V,parseInt(X,10));}else{X=X||"";Q=X.indexOf(O);if(Q>-1){X=X.substring(4);}P=V.toString();if(P===G||Q>-1){V=B.dump(V,parseInt(X,10));}else{V=P;}}}else{if(!B.isString(V)&&!B.isNumber(V)){V="~-"+T.length+"-~";T[T.length]=K;}}Y=Y.substring(0,N)+V+Y.substring(M+1);}for(N=T.length-1;N>=0;N=N-1){Y=Y.replace(new RegExp("~-"+N+"-~"),"{"+T[N]+"}","g");}return Y;},trim:function(I){try{return I.replace(/^\s+|\s+$/g,"");}catch(J){return I;}},merge:function(){var L={},J=arguments,I=J.length,K;for(K=0;K<I;K=K+1){B.augmentObject(L,J[K],true);}return L;},later:function(P,J,Q,L,M){P=P||0;J=J||{};var K=Q,O=L,N,I;if(B.isString(Q)){K=J[Q];}if(!K){throw new TypeError("method undefined");}if(O&&!B.isArray(O)){O=[L];}N=function(){K.apply(J,O||E);};I=(M)?setInterval(N,P):setTimeout(N,P);return{interval:M,cancel:function(){if(this.interval){clearInterval(I);}else{clearTimeout(I);}}};},isValue:function(I){return(B.isObject(I)||B.isString(I)||B.isNumber(I)||B.isBoolean(I));}};B.hasOwnProperty=(A.hasOwnProperty)?function(I,J){return I&&I.hasOwnProperty(J);}:function(I,J){return !B.isUndefined(I[J])&&I.constructor.prototype[J]!==I[J];};D.augmentObject(B,D,true);YAHOO.util.Lang=B;B.augment=B.augmentProto;YAHOO.augment=B.augmentProto;YAHOO.extend=B.extend;})();YAHOO.register("yahoo",YAHOO,{version:"2.8.2r1",build:"7"});
(function(){YAHOO.env._id_counter=YAHOO.env._id_counter||0;var E=YAHOO.util,L=YAHOO.lang,m=YAHOO.env.ua,A=YAHOO.lang.trim,d={},h={},N=/^t(?:able|d|h)$/i,X=/color$/i,K=window.document,W=K.documentElement,e="ownerDocument",n="defaultView",v="documentElement",t="compatMode",b="offsetLeft",P="offsetTop",u="offsetParent",Z="parentNode",l="nodeType",C="tagName",O="scrollLeft",i="scrollTop",Q="getBoundingClientRect",w="getComputedStyle",a="currentStyle",M="CSS1Compat",c="BackCompat",g="class",F="className",J="",B=" ",s="(?:^|\\s)",k="(?= |$)",U="g",p="position",f="fixed",V="relative",j="left",o="top",r="medium",q="borderLeftWidth",R="borderTopWidth",D=m.opera,I=m.webkit,H=m.gecko,T=m.ie;E.Dom={CUSTOM_ATTRIBUTES:(!W.hasAttribute)?{"for":"htmlFor","class":F}:{"htmlFor":"for","className":g},DOT_ATTRIBUTES:{},get:function(z){var AB,x,AA,y,Y,G;if(z){if(z[l]||z.item){return z;}if(typeof z==="string"){AB=z;z=K.getElementById(z);G=(z)?z.attributes:null;if(z&&G&&G.id&&G.id.value===AB){return z;}else{if(z&&K.all){z=null;x=K.all[AB];for(y=0,Y=x.length;y<Y;++y){if(x[y].id===AB){return x[y];}}}}return z;}if(YAHOO.util.Element&&z instanceof YAHOO.util.Element){z=z.get("element");}if("length" in z){AA=[];for(y=0,Y=z.length;y<Y;++y){AA[AA.length]=E.Dom.get(z[y]);}return AA;}return z;}return null;},getComputedStyle:function(G,Y){if(window[w]){return G[e][n][w](G,null)[Y];}else{if(G[a]){return E.Dom.IE_ComputedStyle.get(G,Y);}}},getStyle:function(G,Y){return E.Dom.batch(G,E.Dom._getStyle,Y);},_getStyle:function(){if(window[w]){return function(G,y){y=(y==="float")?y="cssFloat":E.Dom._toCamel(y);var x=G.style[y],Y;if(!x){Y=G[e][n][w](G,null);if(Y){x=Y[y];}}return x;};}else{if(W[a]){return function(G,y){var x;switch(y){case"opacity":x=100;try{x=G.filters["DXImageTransform.Microsoft.Alpha"].opacity;}catch(z){try{x=G.filters("alpha").opacity;}catch(Y){}}return x/100;case"float":y="styleFloat";default:y=E.Dom._toCamel(y);x=G[a]?G[a][y]:null;return(G.style[y]||x);}};}}}(),setStyle:function(G,Y,x){E.Dom.batch(G,E.Dom._setStyle,{prop:Y,val:x});},_setStyle:function(){if(T){return function(Y,G){var x=E.Dom._toCamel(G.prop),y=G.val;if(Y){switch(x){case"opacity":if(L.isString(Y.style.filter)){Y.style.filter="alpha(opacity="+y*100+")";if(!Y[a]||!Y[a].hasLayout){Y.style.zoom=1;}}break;case"float":x="styleFloat";default:Y.style[x]=y;}}else{}};}else{return function(Y,G){var x=E.Dom._toCamel(G.prop),y=G.val;if(Y){if(x=="float"){x="cssFloat";}Y.style[x]=y;}else{}};}}(),getXY:function(G){return E.Dom.batch(G,E.Dom._getXY);},_canPosition:function(G){return(E.Dom._getStyle(G,"display")!=="none"&&E.Dom._inDoc(G));},_getXY:function(){if(K[v][Q]){return function(y){var z,Y,AA,AF,AE,AD,AC,G,x,AB=Math.floor,AG=false;if(E.Dom._canPosition(y)){AA=y[Q]();AF=y[e];z=E.Dom.getDocumentScrollLeft(AF);Y=E.Dom.getDocumentScrollTop(AF);AG=[AB(AA[j]),AB(AA[o])];if(T&&m.ie<8){AE=2;AD=2;AC=AF[t];if(m.ie===6){if(AC!==c){AE=0;AD=0;}}if((AC===c)){G=S(AF[v],q);x=S(AF[v],R);if(G!==r){AE=parseInt(G,10);}if(x!==r){AD=parseInt(x,10);}}AG[0]-=AE;AG[1]-=AD;}if((Y||z)){AG[0]+=z;AG[1]+=Y;}AG[0]=AB(AG[0]);AG[1]=AB(AG[1]);}else{}return AG;};}else{return function(y){var x,Y,AA,AB,AC,z=false,G=y;if(E.Dom._canPosition(y)){z=[y[b],y[P]];x=E.Dom.getDocumentScrollLeft(y[e]);Y=E.Dom.getDocumentScrollTop(y[e]);AC=((H||m.webkit>519)?true:false);while((G=G[u])){z[0]+=G[b];z[1]+=G[P];if(AC){z=E.Dom._calcBorders(G,z);}}if(E.Dom._getStyle(y,p)!==f){G=y;while((G=G[Z])&&G[C]){AA=G[i];AB=G[O];if(H&&(E.Dom._getStyle(G,"overflow")!=="visible")){z=E.Dom._calcBorders(G,z);}if(AA||AB){z[0]-=AB;z[1]-=AA;}}z[0]+=x;z[1]+=Y;}else{if(D){z[0]-=x;z[1]-=Y;}else{if(I||H){z[0]+=x;z[1]+=Y;}}}z[0]=Math.floor(z[0]);z[1]=Math.floor(z[1]);}else{}return z;};}}(),getX:function(G){var Y=function(x){return E.Dom.getXY(x)[0];};return E.Dom.batch(G,Y,E.Dom,true);},getY:function(G){var Y=function(x){return E.Dom.getXY(x)[1];};return E.Dom.batch(G,Y,E.Dom,true);},setXY:function(G,x,Y){E.Dom.batch(G,E.Dom._setXY,{pos:x,noRetry:Y});},_setXY:function(G,z){var AA=E.Dom._getStyle(G,p),y=E.Dom.setStyle,AD=z.pos,Y=z.noRetry,AB=[parseInt(E.Dom.getComputedStyle(G,j),10),parseInt(E.Dom.getComputedStyle(G,o),10)],AC,x;if(AA=="static"){AA=V;y(G,p,AA);}AC=E.Dom._getXY(G);if(!AD||AC===false){return false;}if(isNaN(AB[0])){AB[0]=(AA==V)?0:G[b];}if(isNaN(AB[1])){AB[1]=(AA==V)?0:G[P];}if(AD[0]!==null){y(G,j,AD[0]-AC[0]+AB[0]+"px");}if(AD[1]!==null){y(G,o,AD[1]-AC[1]+AB[1]+"px");}if(!Y){x=E.Dom._getXY(G);if((AD[0]!==null&&x[0]!=AD[0])||(AD[1]!==null&&x[1]!=AD[1])){E.Dom._setXY(G,{pos:AD,noRetry:true});}}},setX:function(Y,G){E.Dom.setXY(Y,[G,null]);},setY:function(G,Y){E.Dom.setXY(G,[null,Y]);},getRegion:function(G){var Y=function(x){var y=false;if(E.Dom._canPosition(x)){y=E.Region.getRegion(x);}else{}return y;};return E.Dom.batch(G,Y,E.Dom,true);},getClientWidth:function(){return E.Dom.getViewportWidth();},getClientHeight:function(){return E.Dom.getViewportHeight();},getElementsByClassName:function(AB,AF,AC,AE,x,AD){AF=AF||"*";AC=(AC)?E.Dom.get(AC):null||K;if(!AC){return[];}var Y=[],G=AC.getElementsByTagName(AF),z=E.Dom.hasClass;for(var y=0,AA=G.length;y<AA;++y){if(z(G[y],AB)){Y[Y.length]=G[y];}}if(AE){E.Dom.batch(Y,AE,x,AD);}return Y;},hasClass:function(Y,G){return E.Dom.batch(Y,E.Dom._hasClass,G);},_hasClass:function(x,Y){var G=false,y;if(x&&Y){y=E.Dom._getAttribute(x,F)||J;if(Y.exec){G=Y.test(y);}else{G=Y&&(B+y+B).indexOf(B+Y+B)>-1;}}else{}return G;},addClass:function(Y,G){return E.Dom.batch(Y,E.Dom._addClass,G);},_addClass:function(x,Y){var G=false,y;if(x&&Y){y=E.Dom._getAttribute(x,F)||J;if(!E.Dom._hasClass(x,Y)){E.Dom.setAttribute(x,F,A(y+B+Y));G=true;}}else{}return G;},removeClass:function(Y,G){return E.Dom.batch(Y,E.Dom._removeClass,G);},_removeClass:function(y,x){var Y=false,AA,z,G;if(y&&x){AA=E.Dom._getAttribute(y,F)||J;E.Dom.setAttribute(y,F,AA.replace(E.Dom._getClassRegex(x),J));z=E.Dom._getAttribute(y,F);if(AA!==z){E.Dom.setAttribute(y,F,A(z));Y=true;if(E.Dom._getAttribute(y,F)===""){G=(y.hasAttribute&&y.hasAttribute(g))?g:F;
y.removeAttribute(G);}}}else{}return Y;},replaceClass:function(x,Y,G){return E.Dom.batch(x,E.Dom._replaceClass,{from:Y,to:G});},_replaceClass:function(y,x){var Y,AB,AA,G=false,z;if(y&&x){AB=x.from;AA=x.to;if(!AA){G=false;}else{if(!AB){G=E.Dom._addClass(y,x.to);}else{if(AB!==AA){z=E.Dom._getAttribute(y,F)||J;Y=(B+z.replace(E.Dom._getClassRegex(AB),B+AA)).split(E.Dom._getClassRegex(AA));Y.splice(1,0,B+AA);E.Dom.setAttribute(y,F,A(Y.join(J)));G=true;}}}}else{}return G;},generateId:function(G,x){x=x||"yui-gen";var Y=function(y){if(y&&y.id){return y.id;}var z=x+YAHOO.env._id_counter++;if(y){if(y[e]&&y[e].getElementById(z)){return E.Dom.generateId(y,z+x);}y.id=z;}return z;};return E.Dom.batch(G,Y,E.Dom,true)||Y.apply(E.Dom,arguments);},isAncestor:function(Y,x){Y=E.Dom.get(Y);x=E.Dom.get(x);var G=false;if((Y&&x)&&(Y[l]&&x[l])){if(Y.contains&&Y!==x){G=Y.contains(x);}else{if(Y.compareDocumentPosition){G=!!(Y.compareDocumentPosition(x)&16);}}}else{}return G;},inDocument:function(G,Y){return E.Dom._inDoc(E.Dom.get(G),Y);},_inDoc:function(Y,x){var G=false;if(Y&&Y[C]){x=x||Y[e];G=E.Dom.isAncestor(x[v],Y);}else{}return G;},getElementsBy:function(Y,AF,AB,AD,y,AC,AE){AF=AF||"*";AB=(AB)?E.Dom.get(AB):null||K;if(!AB){return[];}var x=[],G=AB.getElementsByTagName(AF);for(var z=0,AA=G.length;z<AA;++z){if(Y(G[z])){if(AE){x=G[z];break;}else{x[x.length]=G[z];}}}if(AD){E.Dom.batch(x,AD,y,AC);}return x;},getElementBy:function(x,G,Y){return E.Dom.getElementsBy(x,G,Y,null,null,null,true);},batch:function(x,AB,AA,z){var y=[],Y=(z)?AA:window;x=(x&&(x[C]||x.item))?x:E.Dom.get(x);if(x&&AB){if(x[C]||x.length===undefined){return AB.call(Y,x,AA);}for(var G=0;G<x.length;++G){y[y.length]=AB.call(Y,x[G],AA);}}else{return false;}return y;},getDocumentHeight:function(){var Y=(K[t]!=M||I)?K.body.scrollHeight:W.scrollHeight,G=Math.max(Y,E.Dom.getViewportHeight());return G;},getDocumentWidth:function(){var Y=(K[t]!=M||I)?K.body.scrollWidth:W.scrollWidth,G=Math.max(Y,E.Dom.getViewportWidth());return G;},getViewportHeight:function(){var G=self.innerHeight,Y=K[t];if((Y||T)&&!D){G=(Y==M)?W.clientHeight:K.body.clientHeight;}return G;},getViewportWidth:function(){var G=self.innerWidth,Y=K[t];if(Y||T){G=(Y==M)?W.clientWidth:K.body.clientWidth;}return G;},getAncestorBy:function(G,Y){while((G=G[Z])){if(E.Dom._testElement(G,Y)){return G;}}return null;},getAncestorByClassName:function(Y,G){Y=E.Dom.get(Y);if(!Y){return null;}var x=function(y){return E.Dom.hasClass(y,G);};return E.Dom.getAncestorBy(Y,x);},getAncestorByTagName:function(Y,G){Y=E.Dom.get(Y);if(!Y){return null;}var x=function(y){return y[C]&&y[C].toUpperCase()==G.toUpperCase();};return E.Dom.getAncestorBy(Y,x);},getPreviousSiblingBy:function(G,Y){while(G){G=G.previousSibling;if(E.Dom._testElement(G,Y)){return G;}}return null;},getPreviousSibling:function(G){G=E.Dom.get(G);if(!G){return null;}return E.Dom.getPreviousSiblingBy(G);},getNextSiblingBy:function(G,Y){while(G){G=G.nextSibling;if(E.Dom._testElement(G,Y)){return G;}}return null;},getNextSibling:function(G){G=E.Dom.get(G);if(!G){return null;}return E.Dom.getNextSiblingBy(G);},getFirstChildBy:function(G,x){var Y=(E.Dom._testElement(G.firstChild,x))?G.firstChild:null;return Y||E.Dom.getNextSiblingBy(G.firstChild,x);},getFirstChild:function(G,Y){G=E.Dom.get(G);if(!G){return null;}return E.Dom.getFirstChildBy(G);},getLastChildBy:function(G,x){if(!G){return null;}var Y=(E.Dom._testElement(G.lastChild,x))?G.lastChild:null;return Y||E.Dom.getPreviousSiblingBy(G.lastChild,x);},getLastChild:function(G){G=E.Dom.get(G);return E.Dom.getLastChildBy(G);},getChildrenBy:function(Y,y){var x=E.Dom.getFirstChildBy(Y,y),G=x?[x]:[];E.Dom.getNextSiblingBy(x,function(z){if(!y||y(z)){G[G.length]=z;}return false;});return G;},getChildren:function(G){G=E.Dom.get(G);if(!G){}return E.Dom.getChildrenBy(G);},getDocumentScrollLeft:function(G){G=G||K;return Math.max(G[v].scrollLeft,G.body.scrollLeft);},getDocumentScrollTop:function(G){G=G||K;return Math.max(G[v].scrollTop,G.body.scrollTop);},insertBefore:function(Y,G){Y=E.Dom.get(Y);G=E.Dom.get(G);if(!Y||!G||!G[Z]){return null;}return G[Z].insertBefore(Y,G);},insertAfter:function(Y,G){Y=E.Dom.get(Y);G=E.Dom.get(G);if(!Y||!G||!G[Z]){return null;}if(G.nextSibling){return G[Z].insertBefore(Y,G.nextSibling);}else{return G[Z].appendChild(Y);}},getClientRegion:function(){var x=E.Dom.getDocumentScrollTop(),Y=E.Dom.getDocumentScrollLeft(),y=E.Dom.getViewportWidth()+Y,G=E.Dom.getViewportHeight()+x;return new E.Region(x,y,G,Y);},setAttribute:function(Y,G,x){E.Dom.batch(Y,E.Dom._setAttribute,{attr:G,val:x});},_setAttribute:function(x,Y){var G=E.Dom._toCamel(Y.attr),y=Y.val;if(x&&x.setAttribute){if(E.Dom.DOT_ATTRIBUTES[G]){x[G]=y;}else{G=E.Dom.CUSTOM_ATTRIBUTES[G]||G;x.setAttribute(G,y);}}else{}},getAttribute:function(Y,G){return E.Dom.batch(Y,E.Dom._getAttribute,G);},_getAttribute:function(Y,G){var x;G=E.Dom.CUSTOM_ATTRIBUTES[G]||G;if(Y&&Y.getAttribute){x=Y.getAttribute(G,2);}else{}return x;},_toCamel:function(Y){var x=d;function G(y,z){return z.toUpperCase();}return x[Y]||(x[Y]=Y.indexOf("-")===-1?Y:Y.replace(/-([a-z])/gi,G));},_getClassRegex:function(Y){var G;if(Y!==undefined){if(Y.exec){G=Y;}else{G=h[Y];if(!G){Y=Y.replace(E.Dom._patterns.CLASS_RE_TOKENS,"\\$1");G=h[Y]=new RegExp(s+Y+k,U);}}}return G;},_patterns:{ROOT_TAG:/^body|html$/i,CLASS_RE_TOKENS:/([\.\(\)\^\$\*\+\?\|\[\]\{\}\\])/g},_testElement:function(G,Y){return G&&G[l]==1&&(!Y||Y(G));},_calcBorders:function(x,y){var Y=parseInt(E.Dom[w](x,R),10)||0,G=parseInt(E.Dom[w](x,q),10)||0;if(H){if(N.test(x[C])){Y=0;G=0;}}y[0]+=G;y[1]+=Y;return y;}};var S=E.Dom[w];if(m.opera){E.Dom[w]=function(Y,G){var x=S(Y,G);if(X.test(G)){x=E.Dom.Color.toRGB(x);}return x;};}if(m.webkit){E.Dom[w]=function(Y,G){var x=S(Y,G);if(x==="rgba(0, 0, 0, 0)"){x="transparent";}return x;};}if(m.ie&&m.ie>=8&&K.documentElement.hasAttribute){E.Dom.DOT_ATTRIBUTES.type=true;}})();YAHOO.util.Region=function(C,D,A,B){this.top=C;this.y=C;this[1]=C;this.right=D;this.bottom=A;this.left=B;this.x=B;this[0]=B;
this.width=this.right-this.left;this.height=this.bottom-this.top;};YAHOO.util.Region.prototype.contains=function(A){return(A.left>=this.left&&A.right<=this.right&&A.top>=this.top&&A.bottom<=this.bottom);};YAHOO.util.Region.prototype.getArea=function(){return((this.bottom-this.top)*(this.right-this.left));};YAHOO.util.Region.prototype.intersect=function(E){var C=Math.max(this.top,E.top),D=Math.min(this.right,E.right),A=Math.min(this.bottom,E.bottom),B=Math.max(this.left,E.left);if(A>=C&&D>=B){return new YAHOO.util.Region(C,D,A,B);}else{return null;}};YAHOO.util.Region.prototype.union=function(E){var C=Math.min(this.top,E.top),D=Math.max(this.right,E.right),A=Math.max(this.bottom,E.bottom),B=Math.min(this.left,E.left);return new YAHOO.util.Region(C,D,A,B);};YAHOO.util.Region.prototype.toString=function(){return("Region {"+"top: "+this.top+", right: "+this.right+", bottom: "+this.bottom+", left: "+this.left+", height: "+this.height+", width: "+this.width+"}");};YAHOO.util.Region.getRegion=function(D){var F=YAHOO.util.Dom.getXY(D),C=F[1],E=F[0]+D.offsetWidth,A=F[1]+D.offsetHeight,B=F[0];return new YAHOO.util.Region(C,E,A,B);};YAHOO.util.Point=function(A,B){if(YAHOO.lang.isArray(A)){B=A[1];A=A[0];}YAHOO.util.Point.superclass.constructor.call(this,B,A,B,A);};YAHOO.extend(YAHOO.util.Point,YAHOO.util.Region);(function(){var B=YAHOO.util,A="clientTop",F="clientLeft",J="parentNode",K="right",W="hasLayout",I="px",U="opacity",L="auto",D="borderLeftWidth",G="borderTopWidth",P="borderRightWidth",V="borderBottomWidth",S="visible",Q="transparent",N="height",E="width",H="style",T="currentStyle",R=/^width|height$/,O=/^(\d[.\d]*)+(em|ex|px|gd|rem|vw|vh|vm|ch|mm|cm|in|pt|pc|deg|rad|ms|s|hz|khz|%){1}?/i,M={get:function(X,Z){var Y="",a=X[T][Z];if(Z===U){Y=B.Dom.getStyle(X,U);}else{if(!a||(a.indexOf&&a.indexOf(I)>-1)){Y=a;}else{if(B.Dom.IE_COMPUTED[Z]){Y=B.Dom.IE_COMPUTED[Z](X,Z);}else{if(O.test(a)){Y=B.Dom.IE.ComputedStyle.getPixel(X,Z);}else{Y=a;}}}}return Y;},getOffset:function(Z,e){var b=Z[T][e],X=e.charAt(0).toUpperCase()+e.substr(1),c="offset"+X,Y="pixel"+X,a="",d;if(b==L){d=Z[c];if(d===undefined){a=0;}a=d;if(R.test(e)){Z[H][e]=d;if(Z[c]>d){a=d-(Z[c]-d);}Z[H][e]=L;}}else{if(!Z[H][Y]&&!Z[H][e]){Z[H][e]=b;}a=Z[H][Y];}return a+I;},getBorderWidth:function(X,Z){var Y=null;if(!X[T][W]){X[H].zoom=1;}switch(Z){case G:Y=X[A];break;case V:Y=X.offsetHeight-X.clientHeight-X[A];break;case D:Y=X[F];break;case P:Y=X.offsetWidth-X.clientWidth-X[F];break;}return Y+I;},getPixel:function(Y,X){var a=null,b=Y[T][K],Z=Y[T][X];Y[H][K]=Z;a=Y[H].pixelRight;Y[H][K]=b;return a+I;},getMargin:function(Y,X){var Z;if(Y[T][X]==L){Z=0+I;}else{Z=B.Dom.IE.ComputedStyle.getPixel(Y,X);}return Z;},getVisibility:function(Y,X){var Z;while((Z=Y[T])&&Z[X]=="inherit"){Y=Y[J];}return(Z)?Z[X]:S;},getColor:function(Y,X){return B.Dom.Color.toRGB(Y[T][X])||Q;},getBorderColor:function(Y,X){var Z=Y[T],a=Z[X]||Z.color;return B.Dom.Color.toRGB(B.Dom.Color.toHex(a));}},C={};C.top=C.right=C.bottom=C.left=C[E]=C[N]=M.getOffset;C.color=M.getColor;C[G]=C[P]=C[V]=C[D]=M.getBorderWidth;C.marginTop=C.marginRight=C.marginBottom=C.marginLeft=M.getMargin;C.visibility=M.getVisibility;C.borderColor=C.borderTopColor=C.borderRightColor=C.borderBottomColor=C.borderLeftColor=M.getBorderColor;B.Dom.IE_COMPUTED=C;B.Dom.IE_ComputedStyle=M;})();(function(){var C="toString",A=parseInt,B=RegExp,D=YAHOO.util;D.Dom.Color={KEYWORDS:{black:"000",silver:"c0c0c0",gray:"808080",white:"fff",maroon:"800000",red:"f00",purple:"800080",fuchsia:"f0f",green:"008000",lime:"0f0",olive:"808000",yellow:"ff0",navy:"000080",blue:"00f",teal:"008080",aqua:"0ff"},re_RGB:/^rgb\(([0-9]+)\s*,\s*([0-9]+)\s*,\s*([0-9]+)\)$/i,re_hex:/^#?([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})$/i,re_hex3:/([0-9A-F])/gi,toRGB:function(E){if(!D.Dom.Color.re_RGB.test(E)){E=D.Dom.Color.toHex(E);}if(D.Dom.Color.re_hex.exec(E)){E="rgb("+[A(B.$1,16),A(B.$2,16),A(B.$3,16)].join(", ")+")";}return E;},toHex:function(H){H=D.Dom.Color.KEYWORDS[H]||H;if(D.Dom.Color.re_RGB.exec(H)){var G=(B.$1.length===1)?"0"+B.$1:Number(B.$1),F=(B.$2.length===1)?"0"+B.$2:Number(B.$2),E=(B.$3.length===1)?"0"+B.$3:Number(B.$3);H=[G[C](16),F[C](16),E[C](16)].join("");}if(H.length<6){H=H.replace(D.Dom.Color.re_hex3,"$1$1");}if(H!=="transparent"&&H.indexOf("#")<0){H="#"+H;}return H.toLowerCase();}};}());YAHOO.register("dom",YAHOO.util.Dom,{version:"2.8.2r1",build:"7"});YAHOO.util.CustomEvent=function(D,C,B,A,E){this.type=D;this.scope=C||window;this.silent=B;this.fireOnce=E;this.fired=false;this.firedWith=null;this.signature=A||YAHOO.util.CustomEvent.LIST;this.subscribers=[];if(!this.silent){}var F="_YUICEOnSubscribe";if(D!==F){this.subscribeEvent=new YAHOO.util.CustomEvent(F,this,true);}this.lastError=null;};YAHOO.util.CustomEvent.LIST=0;YAHOO.util.CustomEvent.FLAT=1;YAHOO.util.CustomEvent.prototype={subscribe:function(B,C,D){if(!B){throw new Error("Invalid callback for subscriber to '"+this.type+"'");}if(this.subscribeEvent){this.subscribeEvent.fire(B,C,D);}var A=new YAHOO.util.Subscriber(B,C,D);if(this.fireOnce&&this.fired){this.notify(A,this.firedWith);}else{this.subscribers.push(A);}},unsubscribe:function(D,F){if(!D){return this.unsubscribeAll();}var E=false;for(var B=0,A=this.subscribers.length;B<A;++B){var C=this.subscribers[B];if(C&&C.contains(D,F)){this._delete(B);E=true;}}return E;},fire:function(){this.lastError=null;var H=[],A=this.subscribers.length;var D=[].slice.call(arguments,0),C=true,F,B=false;if(this.fireOnce){if(this.fired){return true;}else{this.firedWith=D;}}this.fired=true;if(!A&&this.silent){return true;}if(!this.silent){}var E=this.subscribers.slice();for(F=0;F<A;++F){var G=E[F];if(!G){B=true;}else{C=this.notify(G,D);if(false===C){if(!this.silent){}break;}}}return(C!==false);},notify:function(F,C){var B,H=null,E=F.getScope(this.scope),A=YAHOO.util.Event.throwErrors;if(!this.silent){}if(this.signature==YAHOO.util.CustomEvent.FLAT){if(C.length>0){H=C[0];}try{B=F.fn.call(E,H,F.obj);}catch(G){this.lastError=G;if(A){throw G;}}}else{try{B=F.fn.call(E,this.type,C,F.obj);}catch(D){this.lastError=D;if(A){throw D;}}}return B;},unsubscribeAll:function(){var A=this.subscribers.length,B;for(B=A-1;B>-1;B--){this._delete(B);}this.subscribers=[];return A;},_delete:function(A){var B=this.subscribers[A];if(B){delete B.fn;delete B.obj;}this.subscribers.splice(A,1);},toString:function(){return"CustomEvent: "+"'"+this.type+"', "+"context: "+this.scope;}};YAHOO.util.Subscriber=function(A,B,C){this.fn=A;this.obj=YAHOO.lang.isUndefined(B)?null:B;this.overrideContext=C;};YAHOO.util.Subscriber.prototype.getScope=function(A){if(this.overrideContext){if(this.overrideContext===true){return this.obj;}else{return this.overrideContext;}}return A;};YAHOO.util.Subscriber.prototype.contains=function(A,B){if(B){return(this.fn==A&&this.obj==B);}else{return(this.fn==A);}};YAHOO.util.Subscriber.prototype.toString=function(){return"Subscriber { obj: "+this.obj+", overrideContext: "+(this.overrideContext||"no")+" }";};if(!YAHOO.util.Event){YAHOO.util.Event=function(){var G=false,H=[],J=[],A=0,E=[],B=0,C={63232:38,63233:40,63234:37,63235:39,63276:33,63277:34,25:9},D=YAHOO.env.ua.ie,F="focusin",I="focusout";return{POLL_RETRYS:500,POLL_INTERVAL:40,EL:0,TYPE:1,FN:2,WFN:3,UNLOAD_OBJ:3,ADJ_SCOPE:4,OBJ:5,OVERRIDE:6,CAPTURE:7,lastError:null,isSafari:YAHOO.env.ua.webkit,webkit:YAHOO.env.ua.webkit,isIE:D,_interval:null,_dri:null,_specialTypes:{focusin:(D?"focusin":"focus"),focusout:(D?"focusout":"blur")},DOMReady:false,throwErrors:false,startInterval:function(){if(!this._interval){this._interval=YAHOO.lang.later(this.POLL_INTERVAL,this,this._tryPreloadAttach,null,true);}},onAvailable:function(Q,M,O,P,N){var K=(YAHOO.lang.isString(Q))?[Q]:Q;for(var L=0;L<K.length;L=L+1){E.push({id:K[L],fn:M,obj:O,overrideContext:P,checkReady:N});}A=this.POLL_RETRYS;this.startInterval();},onContentReady:function(N,K,L,M){this.onAvailable(N,K,L,M,true);},onDOMReady:function(){this.DOMReadyEvent.subscribe.apply(this.DOMReadyEvent,arguments);},_addListener:function(M,K,V,P,T,Y){if(!V||!V.call){return false;}if(this._isValidCollection(M)){var W=true;for(var Q=0,S=M.length;Q<S;++Q){W=this.on(M[Q],K,V,P,T)&&W;}return W;}else{if(YAHOO.lang.isString(M)){var O=this.getEl(M);if(O){M=O;}else{this.onAvailable(M,function(){YAHOO.util.Event._addListener(M,K,V,P,T,Y);});return true;}}}if(!M){return false;}if("unload"==K&&P!==this){J[J.length]=[M,K,V,P,T];return true;}var L=M;if(T){if(T===true){L=P;}else{L=T;}}var N=function(Z){return V.call(L,YAHOO.util.Event.getEvent(Z,M),P);};var X=[M,K,V,N,L,P,T,Y];var R=H.length;H[R]=X;try{this._simpleAdd(M,K,N,Y);}catch(U){this.lastError=U;this.removeListener(M,K,V);return false;}return true;},_getType:function(K){return this._specialTypes[K]||K;},addListener:function(M,P,L,N,O){var K=((P==F||P==I)&&!YAHOO.env.ua.ie)?true:false;return this._addListener(M,this._getType(P),L,N,O,K);},addFocusListener:function(L,K,M,N){return this.on(L,F,K,M,N);},removeFocusListener:function(L,K){return this.removeListener(L,F,K);},addBlurListener:function(L,K,M,N){return this.on(L,I,K,M,N);},removeBlurListener:function(L,K){return this.removeListener(L,I,K);},removeListener:function(L,K,R){var M,P,U;K=this._getType(K);if(typeof L=="string"){L=this.getEl(L);}else{if(this._isValidCollection(L)){var S=true;for(M=L.length-1;M>-1;M--){S=(this.removeListener(L[M],K,R)&&S);}return S;}}if(!R||!R.call){return this.purgeElement(L,false,K);}if("unload"==K){for(M=J.length-1;M>-1;M--){U=J[M];if(U&&U[0]==L&&U[1]==K&&U[2]==R){J.splice(M,1);return true;}}return false;}var N=null;var O=arguments[3];if("undefined"===typeof O){O=this._getCacheIndex(H,L,K,R);}if(O>=0){N=H[O];}if(!L||!N){return false;}var T=N[this.CAPTURE]===true?true:false;try{this._simpleRemove(L,K,N[this.WFN],T);}catch(Q){this.lastError=Q;return false;}delete H[O][this.WFN];delete H[O][this.FN];H.splice(O,1);return true;},getTarget:function(M,L){var K=M.target||M.srcElement;return this.resolveTextNode(K);},resolveTextNode:function(L){try{if(L&&3==L.nodeType){return L.parentNode;}}catch(K){}return L;},getPageX:function(L){var K=L.pageX;if(!K&&0!==K){K=L.clientX||0;if(this.isIE){K+=this._getScrollLeft();}}return K;},getPageY:function(K){var L=K.pageY;if(!L&&0!==L){L=K.clientY||0;if(this.isIE){L+=this._getScrollTop();}}return L;},getXY:function(K){return[this.getPageX(K),this.getPageY(K)];},getRelatedTarget:function(L){var K=L.relatedTarget;if(!K){if(L.type=="mouseout"){K=L.toElement;
}else{if(L.type=="mouseover"){K=L.fromElement;}}}return this.resolveTextNode(K);},getTime:function(M){if(!M.time){var L=new Date().getTime();try{M.time=L;}catch(K){this.lastError=K;return L;}}return M.time;},stopEvent:function(K){this.stopPropagation(K);this.preventDefault(K);},stopPropagation:function(K){if(K.stopPropagation){K.stopPropagation();}else{K.cancelBubble=true;}},preventDefault:function(K){if(K.preventDefault){K.preventDefault();}else{K.returnValue=false;}},getEvent:function(M,K){var L=M||window.event;if(!L){var N=this.getEvent.caller;while(N){L=N.arguments[0];if(L&&Event==L.constructor){break;}N=N.caller;}}return L;},getCharCode:function(L){var K=L.keyCode||L.charCode||0;if(YAHOO.env.ua.webkit&&(K in C)){K=C[K];}return K;},_getCacheIndex:function(M,P,Q,O){for(var N=0,L=M.length;N<L;N=N+1){var K=M[N];if(K&&K[this.FN]==O&&K[this.EL]==P&&K[this.TYPE]==Q){return N;}}return -1;},generateId:function(K){var L=K.id;if(!L){L="yuievtautoid-"+B;++B;K.id=L;}return L;},_isValidCollection:function(L){try{return(L&&typeof L!=="string"&&L.length&&!L.tagName&&!L.alert&&typeof L[0]!=="undefined");}catch(K){return false;}},elCache:{},getEl:function(K){return(typeof K==="string")?document.getElementById(K):K;},clearCache:function(){},DOMReadyEvent:new YAHOO.util.CustomEvent("DOMReady",YAHOO,0,0,1),_load:function(L){if(!G){G=true;var K=YAHOO.util.Event;K._ready();K._tryPreloadAttach();}},_ready:function(L){var K=YAHOO.util.Event;if(!K.DOMReady){K.DOMReady=true;K.DOMReadyEvent.fire();K._simpleRemove(document,"DOMContentLoaded",K._ready);}},_tryPreloadAttach:function(){if(E.length===0){A=0;if(this._interval){this._interval.cancel();this._interval=null;}return;}if(this.locked){return;}if(this.isIE){if(!this.DOMReady){this.startInterval();return;}}this.locked=true;var Q=!G;if(!Q){Q=(A>0&&E.length>0);}var P=[];var R=function(T,U){var S=T;if(U.overrideContext){if(U.overrideContext===true){S=U.obj;}else{S=U.overrideContext;}}U.fn.call(S,U.obj);};var L,K,O,N,M=[];for(L=0,K=E.length;L<K;L=L+1){O=E[L];if(O){N=this.getEl(O.id);if(N){if(O.checkReady){if(G||N.nextSibling||!Q){M.push(O);E[L]=null;}}else{R(N,O);E[L]=null;}}else{P.push(O);}}}for(L=0,K=M.length;L<K;L=L+1){O=M[L];R(this.getEl(O.id),O);}A--;if(Q){for(L=E.length-1;L>-1;L--){O=E[L];if(!O||!O.id){E.splice(L,1);}}this.startInterval();}else{if(this._interval){this._interval.cancel();this._interval=null;}}this.locked=false;},purgeElement:function(O,P,R){var M=(YAHOO.lang.isString(O))?this.getEl(O):O;var Q=this.getListeners(M,R),N,K;if(Q){for(N=Q.length-1;N>-1;N--){var L=Q[N];this.removeListener(M,L.type,L.fn);}}if(P&&M&&M.childNodes){for(N=0,K=M.childNodes.length;N<K;++N){this.purgeElement(M.childNodes[N],P,R);}}},getListeners:function(M,K){var P=[],L;if(!K){L=[H,J];}else{if(K==="unload"){L=[J];}else{K=this._getType(K);L=[H];}}var R=(YAHOO.lang.isString(M))?this.getEl(M):M;for(var O=0;O<L.length;O=O+1){var T=L[O];if(T){for(var Q=0,S=T.length;Q<S;++Q){var N=T[Q];if(N&&N[this.EL]===R&&(!K||K===N[this.TYPE])){P.push({type:N[this.TYPE],fn:N[this.FN],obj:N[this.OBJ],adjust:N[this.OVERRIDE],scope:N[this.ADJ_SCOPE],index:Q});}}}}return(P.length)?P:null;},_unload:function(R){var L=YAHOO.util.Event,O,N,M,Q,P,S=J.slice(),K;for(O=0,Q=J.length;O<Q;++O){M=S[O];if(M){K=window;if(M[L.ADJ_SCOPE]){if(M[L.ADJ_SCOPE]===true){K=M[L.UNLOAD_OBJ];}else{K=M[L.ADJ_SCOPE];}}M[L.FN].call(K,L.getEvent(R,M[L.EL]),M[L.UNLOAD_OBJ]);S[O]=null;}}M=null;K=null;J=null;if(H){for(N=H.length-1;N>-1;N--){M=H[N];if(M){L.removeListener(M[L.EL],M[L.TYPE],M[L.FN],N);}}M=null;}L._simpleRemove(window,"unload",L._unload);},_getScrollLeft:function(){return this._getScroll()[1];},_getScrollTop:function(){return this._getScroll()[0];},_getScroll:function(){var K=document.documentElement,L=document.body;if(K&&(K.scrollTop||K.scrollLeft)){return[K.scrollTop,K.scrollLeft];}else{if(L){return[L.scrollTop,L.scrollLeft];}else{return[0,0];}}},regCE:function(){},_simpleAdd:function(){if(window.addEventListener){return function(M,N,L,K){M.addEventListener(N,L,(K));};}else{if(window.attachEvent){return function(M,N,L,K){M.attachEvent("on"+N,L);};}else{return function(){};}}}(),_simpleRemove:function(){if(window.removeEventListener){return function(M,N,L,K){M.removeEventListener(N,L,(K));};}else{if(window.detachEvent){return function(L,M,K){L.detachEvent("on"+M,K);};}else{return function(){};}}}()};}();(function(){var EU=YAHOO.util.Event;EU.on=EU.addListener;EU.onFocus=EU.addFocusListener;EU.onBlur=EU.addBlurListener;
/* DOMReady: based on work by: Dean Edwards/John Resig/Matthias Miller/Diego Perini */
if(EU.isIE){if(self!==self.top){document.onreadystatechange=function(){if(document.readyState=="complete"){document.onreadystatechange=null;EU._ready();}};}else{YAHOO.util.Event.onDOMReady(YAHOO.util.Event._tryPreloadAttach,YAHOO.util.Event,true);var n=document.createElement("p");EU._dri=setInterval(function(){try{n.doScroll("left");clearInterval(EU._dri);EU._dri=null;EU._ready();n=null;}catch(ex){}},EU.POLL_INTERVAL);}}else{if(EU.webkit&&EU.webkit<525){EU._dri=setInterval(function(){var rs=document.readyState;if("loaded"==rs||"complete"==rs){clearInterval(EU._dri);EU._dri=null;EU._ready();}},EU.POLL_INTERVAL);}else{EU._simpleAdd(document,"DOMContentLoaded",EU._ready);}}EU._simpleAdd(window,"load",EU._load);EU._simpleAdd(window,"unload",EU._unload);EU._tryPreloadAttach();})();}YAHOO.util.EventProvider=function(){};YAHOO.util.EventProvider.prototype={__yui_events:null,__yui_subscribers:null,subscribe:function(A,C,F,E){this.__yui_events=this.__yui_events||{};var D=this.__yui_events[A];if(D){D.subscribe(C,F,E);}else{this.__yui_subscribers=this.__yui_subscribers||{};var B=this.__yui_subscribers;if(!B[A]){B[A]=[];}B[A].push({fn:C,obj:F,overrideContext:E});}},unsubscribe:function(C,E,G){this.__yui_events=this.__yui_events||{};var A=this.__yui_events;if(C){var F=A[C];if(F){return F.unsubscribe(E,G);}}else{var B=true;for(var D in A){if(YAHOO.lang.hasOwnProperty(A,D)){B=B&&A[D].unsubscribe(E,G);}}return B;}return false;},unsubscribeAll:function(A){return this.unsubscribe(A);
},createEvent:function(B,G){this.__yui_events=this.__yui_events||{};var E=G||{},D=this.__yui_events,F;if(D[B]){}else{F=new YAHOO.util.CustomEvent(B,E.scope||this,E.silent,YAHOO.util.CustomEvent.FLAT,E.fireOnce);D[B]=F;if(E.onSubscribeCallback){F.subscribeEvent.subscribe(E.onSubscribeCallback);}this.__yui_subscribers=this.__yui_subscribers||{};var A=this.__yui_subscribers[B];if(A){for(var C=0;C<A.length;++C){F.subscribe(A[C].fn,A[C].obj,A[C].overrideContext);}}}return D[B];},fireEvent:function(B){this.__yui_events=this.__yui_events||{};var D=this.__yui_events[B];if(!D){return null;}var A=[];for(var C=1;C<arguments.length;++C){A.push(arguments[C]);}return D.fire.apply(D,A);},hasEvent:function(A){if(this.__yui_events){if(this.__yui_events[A]){return true;}}return false;}};(function(){var A=YAHOO.util.Event,C=YAHOO.lang;YAHOO.util.KeyListener=function(D,I,E,F){if(!D){}else{if(!I){}else{if(!E){}}}if(!F){F=YAHOO.util.KeyListener.KEYDOWN;}var G=new YAHOO.util.CustomEvent("keyPressed");this.enabledEvent=new YAHOO.util.CustomEvent("enabled");this.disabledEvent=new YAHOO.util.CustomEvent("disabled");if(C.isString(D)){D=document.getElementById(D);}if(C.isFunction(E)){G.subscribe(E);}else{G.subscribe(E.fn,E.scope,E.correctScope);}function H(O,N){if(!I.shift){I.shift=false;}if(!I.alt){I.alt=false;}if(!I.ctrl){I.ctrl=false;}if(O.shiftKey==I.shift&&O.altKey==I.alt&&O.ctrlKey==I.ctrl){var J,M=I.keys,L;if(YAHOO.lang.isArray(M)){for(var K=0;K<M.length;K++){J=M[K];L=A.getCharCode(O);if(J==L){G.fire(L,O);break;}}}else{L=A.getCharCode(O);if(M==L){G.fire(L,O);}}}}this.enable=function(){if(!this.enabled){A.on(D,F,H);this.enabledEvent.fire(I);}this.enabled=true;};this.disable=function(){if(this.enabled){A.removeListener(D,F,H);this.disabledEvent.fire(I);}this.enabled=false;};this.toString=function(){return"KeyListener ["+I.keys+"] "+D.tagName+(D.id?"["+D.id+"]":"");};};var B=YAHOO.util.KeyListener;B.KEYDOWN="keydown";B.KEYUP="keyup";B.KEY={ALT:18,BACK_SPACE:8,CAPS_LOCK:20,CONTROL:17,DELETE:46,DOWN:40,END:35,ENTER:13,ESCAPE:27,HOME:36,LEFT:37,META:224,NUM_LOCK:144,PAGE_DOWN:34,PAGE_UP:33,PAUSE:19,PRINTSCREEN:44,RIGHT:39,SCROLL_LOCK:145,SHIFT:16,SPACE:32,TAB:9,UP:38};})();YAHOO.register("event",YAHOO.util.Event,{version:"2.8.2r1",build:"7"});YAHOO.register("yahoo-dom-event", YAHOO, {version: "2.8.2r1", build: "7"});
/*
 * file from "UI\system\service\doc\transport\elementMin.js"
 */

/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 2.8.2r1
*/
YAHOO.util.Attribute=function(B,A){if(A){this.owner=A;this.configure(B,true);}};YAHOO.util.Attribute.prototype={name:undefined,value:null,owner:null,readOnly:false,writeOnce:false,_initialConfig:null,_written:false,method:null,setter:null,getter:null,validator:null,getValue:function(){var A=this.value;if(this.getter){A=this.getter.call(this.owner,this.name,A);}return A;},setValue:function(F,B){var E,A=this.owner,C=this.name;var D={type:C,prevValue:this.getValue(),newValue:F};if(this.readOnly||(this.writeOnce&&this._written)){return false;}if(this.validator&&!this.validator.call(A,F)){return false;}if(!B){E=A.fireBeforeChangeEvent(D);if(E===false){return false;}}if(this.setter){F=this.setter.call(A,F,this.name);if(F===undefined){}}if(this.method){this.method.call(A,F,this.name);}this.value=F;this._written=true;D.type=C;if(!B){this.owner.fireChangeEvent(D);}return true;},configure:function(B,C){B=B||{};if(C){this._written=false;}this._initialConfig=this._initialConfig||{};for(var A in B){if(B.hasOwnProperty(A)){this[A]=B[A];if(C){this._initialConfig[A]=B[A];}}}},resetValue:function(){return this.setValue(this._initialConfig.value);},resetConfig:function(){this.configure(this._initialConfig,true);},refresh:function(A){this.setValue(this.value,A);}};(function(){var A=YAHOO.util.Lang;YAHOO.util.AttributeProvider=function(){};YAHOO.util.AttributeProvider.prototype={_configs:null,get:function(C){this._configs=this._configs||{};var B=this._configs[C];if(!B||!this._configs.hasOwnProperty(C)){return null;}return B.getValue();},set:function(D,E,B){this._configs=this._configs||{};var C=this._configs[D];if(!C){return false;}return C.setValue(E,B);},getAttributeKeys:function(){this._configs=this._configs;var C=[],B;for(B in this._configs){if(A.hasOwnProperty(this._configs,B)&&!A.isUndefined(this._configs[B])){C[C.length]=B;}}return C;},setAttributes:function(D,B){for(var C in D){if(A.hasOwnProperty(D,C)){this.set(C,D[C],B);}}},resetValue:function(C,B){this._configs=this._configs||{};if(this._configs[C]){this.set(C,this._configs[C]._initialConfig.value,B);return true;}return false;},refresh:function(E,C){this._configs=this._configs||{};var F=this._configs;E=((A.isString(E))?[E]:E)||this.getAttributeKeys();for(var D=0,B=E.length;D<B;++D){if(F.hasOwnProperty(E[D])){this._configs[E[D]].refresh(C);}}},register:function(B,C){this.setAttributeConfig(B,C);},getAttributeConfig:function(C){this._configs=this._configs||{};var B=this._configs[C]||{};var D={};for(C in B){if(A.hasOwnProperty(B,C)){D[C]=B[C];}}return D;},setAttributeConfig:function(B,C,D){this._configs=this._configs||{};C=C||{};if(!this._configs[B]){C.name=B;this._configs[B]=this.createAttribute(C);}else{this._configs[B].configure(C,D);}},configureAttribute:function(B,C,D){this.setAttributeConfig(B,C,D);},resetAttributeConfig:function(B){this._configs=this._configs||{};this._configs[B].resetConfig();},subscribe:function(B,C){this._events=this._events||{};if(!(B in this._events)){this._events[B]=this.createEvent(B);}YAHOO.util.EventProvider.prototype.subscribe.apply(this,arguments);},on:function(){this.subscribe.apply(this,arguments);},addListener:function(){this.subscribe.apply(this,arguments);},fireBeforeChangeEvent:function(C){var B="before";B+=C.type.charAt(0).toUpperCase()+C.type.substr(1)+"Change";C.type=B;return this.fireEvent(C.type,C);},fireChangeEvent:function(B){B.type+="Change";return this.fireEvent(B.type,B);},createAttribute:function(B){return new YAHOO.util.Attribute(B,this);}};YAHOO.augment(YAHOO.util.AttributeProvider,YAHOO.util.EventProvider);})();(function(){var B=YAHOO.util.Dom,D=YAHOO.util.AttributeProvider,C={mouseenter:true,mouseleave:true};var A=function(E,F){this.init.apply(this,arguments);};A.DOM_EVENTS={"click":true,"dblclick":true,"keydown":true,"keypress":true,"keyup":true,"mousedown":true,"mousemove":true,"mouseout":true,"mouseover":true,"mouseup":true,"mouseenter":true,"mouseleave":true,"focus":true,"blur":true,"submit":true,"change":true};A.prototype={DOM_EVENTS:null,DEFAULT_HTML_SETTER:function(G,E){var F=this.get("element");if(F){F[E]=G;}return G;},DEFAULT_HTML_GETTER:function(E){var F=this.get("element"),G;if(F){G=F[E];}return G;},appendChild:function(E){E=E.get?E.get("element"):E;return this.get("element").appendChild(E);},getElementsByTagName:function(E){return this.get("element").getElementsByTagName(E);},hasChildNodes:function(){return this.get("element").hasChildNodes();},insertBefore:function(E,F){E=E.get?E.get("element"):E;F=(F&&F.get)?F.get("element"):F;return this.get("element").insertBefore(E,F);},removeChild:function(E){E=E.get?E.get("element"):E;return this.get("element").removeChild(E);},replaceChild:function(E,F){E=E.get?E.get("element"):E;F=F.get?F.get("element"):F;return this.get("element").replaceChild(E,F);},initAttributes:function(E){},addListener:function(J,I,K,H){H=H||this;var E=YAHOO.util.Event,G=this.get("element")||this.get("id"),F=this;if(C[J]&&!E._createMouseDelegate){return false;}if(!this._events[J]){if(G&&this.DOM_EVENTS[J]){E.on(G,J,function(M,L){if(M.srcElement&&!M.target){M.target=M.srcElement;}if((M.toElement&&!M.relatedTarget)||(M.fromElement&&!M.relatedTarget)){M.relatedTarget=E.getRelatedTarget(M);}if(!M.currentTarget){M.currentTarget=G;}F.fireEvent(J,M,L);},K,H);}this.createEvent(J,{scope:this});}return YAHOO.util.EventProvider.prototype.subscribe.apply(this,arguments);},on:function(){return this.addListener.apply(this,arguments);},subscribe:function(){return this.addListener.apply(this,arguments);},removeListener:function(F,E){return this.unsubscribe.apply(this,arguments);},addClass:function(E){B.addClass(this.get("element"),E);},getElementsByClassName:function(F,E){return B.getElementsByClassName(F,E,this.get("element"));},hasClass:function(E){return B.hasClass(this.get("element"),E);},removeClass:function(E){return B.removeClass(this.get("element"),E);},replaceClass:function(F,E){return B.replaceClass(this.get("element"),F,E);},setStyle:function(F,E){return B.setStyle(this.get("element"),F,E);
},getStyle:function(E){return B.getStyle(this.get("element"),E);},fireQueue:function(){var F=this._queue;for(var G=0,E=F.length;G<E;++G){this[F[G][0]].apply(this,F[G][1]);}},appendTo:function(F,G){F=(F.get)?F.get("element"):B.get(F);this.fireEvent("beforeAppendTo",{type:"beforeAppendTo",target:F});G=(G&&G.get)?G.get("element"):B.get(G);var E=this.get("element");if(!E){return false;}if(!F){return false;}if(E.parent!=F){if(G){F.insertBefore(E,G);}else{F.appendChild(E);}}this.fireEvent("appendTo",{type:"appendTo",target:F});return E;},get:function(E){var G=this._configs||{},F=G.element;if(F&&!G[E]&&!YAHOO.lang.isUndefined(F.value[E])){this._setHTMLAttrConfig(E);}return D.prototype.get.call(this,E);},setAttributes:function(K,H){var F={},I=this._configOrder;for(var J=0,E=I.length;J<E;++J){if(K[I[J]]!==undefined){F[I[J]]=true;this.set(I[J],K[I[J]],H);}}for(var G in K){if(K.hasOwnProperty(G)&&!F[G]){this.set(G,K[G],H);}}},set:function(F,H,E){var G=this.get("element");if(!G){this._queue[this._queue.length]=["set",arguments];if(this._configs[F]){this._configs[F].value=H;}return;}if(!this._configs[F]&&!YAHOO.lang.isUndefined(G[F])){this._setHTMLAttrConfig(F);}return D.prototype.set.apply(this,arguments);},setAttributeConfig:function(E,F,G){this._configOrder.push(E);D.prototype.setAttributeConfig.apply(this,arguments);},createEvent:function(F,E){this._events[F]=true;return D.prototype.createEvent.apply(this,arguments);},init:function(F,E){this._initElement(F,E);},destroy:function(){var E=this.get("element");YAHOO.util.Event.purgeElement(E,true);this.unsubscribeAll();if(E&&E.parentNode){E.parentNode.removeChild(E);}this._queue=[];this._events={};this._configs={};this._configOrder=[];},_initElement:function(G,F){this._queue=this._queue||[];this._events=this._events||{};this._configs=this._configs||{};this._configOrder=[];F=F||{};F.element=F.element||G||null;var I=false;var E=A.DOM_EVENTS;this.DOM_EVENTS=this.DOM_EVENTS||{};for(var H in E){if(E.hasOwnProperty(H)){this.DOM_EVENTS[H]=E[H];}}if(typeof F.element==="string"){this._setHTMLAttrConfig("id",{value:F.element});}if(B.get(F.element)){I=true;this._initHTMLElement(F);this._initContent(F);}YAHOO.util.Event.onAvailable(F.element,function(){if(!I){this._initHTMLElement(F);}this.fireEvent("available",{type:"available",target:B.get(F.element)});},this,true);YAHOO.util.Event.onContentReady(F.element,function(){if(!I){this._initContent(F);}this.fireEvent("contentReady",{type:"contentReady",target:B.get(F.element)});},this,true);},_initHTMLElement:function(E){this.setAttributeConfig("element",{value:B.get(E.element),readOnly:true});},_initContent:function(E){this.initAttributes(E);this.setAttributes(E,true);this.fireQueue();},_setHTMLAttrConfig:function(E,G){var F=this.get("element");G=G||{};G.name=E;G.setter=G.setter||this.DEFAULT_HTML_SETTER;G.getter=G.getter||this.DEFAULT_HTML_GETTER;G.value=G.value||F[E];this._configs[E]=new YAHOO.util.Attribute(G,this);}};YAHOO.augment(A,D);YAHOO.util.Element=A;})();YAHOO.register("element",YAHOO.util.Element,{version:"2.8.2r1",build:"7"});
/*
 * file from "UI\system\service\doc\transport\uploaderExperimental.js"
 */

/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 2.8.2r1
*/
/*extern ActiveXObject, __flash_unloadHandler, __flash_savedUnloadHandler */
/*!
 * SWFObject v1.5: Flash Player detection and embed - http://blog.deconcept.com/swfobject/
 *
 * SWFObject is (c) 2007 Geoff Stearns and is released under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 * @namespace YAHOO
 */

YAHOO.namespace("deconcept"); 
	
YAHOO.deconcept = YAHOO.deconcept || {};

if(typeof YAHOO.deconcept.util == "undefined" || !YAHOO.deconcept.util)
{
	YAHOO.deconcept.util = {};
}

if(typeof YAHOO.deconcept.SWFObjectUtil == "undefined" || !YAHOO.deconcept.SWFObjectUtil)
{
	YAHOO.deconcept.SWFObjectUtil = {};
}

YAHOO.deconcept.SWFObject = function(swf, id, w, h, ver, c, quality, xiRedirectUrl, redirectUrl, detectKey)
{
	if(!document.getElementById) { return; }
	this.DETECT_KEY = detectKey ? detectKey : 'detectflash';
	this.skipDetect = YAHOO.deconcept.util.getRequestParameter(this.DETECT_KEY);
	this.params = {};
	this.variables = {};
	this.attributes = [];
	if(swf) { this.setAttribute('swf', swf); }
	if(id) { this.setAttribute('id', id); }
	if(w) { this.setAttribute('width', w); }
	if(h) { this.setAttribute('height', h); }
	if(ver) { this.setAttribute('version', new YAHOO.deconcept.PlayerVersion(ver.toString().split("."))); }
	this.installedVer = YAHOO.deconcept.SWFObjectUtil.getPlayerVersion();
	if (!window.opera && document.all && this.installedVer.major > 7)
	{
		// only add the onunload cleanup if the Flash Player version supports External Interface and we are in IE
		YAHOO.deconcept.SWFObject.doPrepUnload = true;
	}
	if(c)
	{
		this.addParam('bgcolor', c);
	}
	var q = quality ? quality : 'high';
	this.addParam('quality', q);
	this.setAttribute('useExpressInstall', false);
	this.setAttribute('doExpressInstall', false);
	var xir = (xiRedirectUrl) ? xiRedirectUrl : window.location;
	this.setAttribute('xiRedirectUrl', xir);
	this.setAttribute('redirectUrl', '');
	if(redirectUrl)
	{
		this.setAttribute('redirectUrl', redirectUrl);
	}
};

YAHOO.deconcept.SWFObject.prototype =
{
	useExpressInstall: function(path)
	{
		this.xiSWFPath = !path ? "expressinstall.swf" : path;
		this.setAttribute('useExpressInstall', true);
	},
	setAttribute: function(name, value){
		this.attributes[name] = value;
	},
	getAttribute: function(name){
		return this.attributes[name];
	},
	addParam: function(name, value){
		this.params[name] = value;
	},
	getParams: function(){
		return this.params;
	},
	addVariable: function(name, value){
		this.variables[name] = value;
	},
	getVariable: function(name){
		return this.variables[name];
	},
	getVariables: function(){
		return this.variables;
	},
	getVariablePairs: function(){
		var variablePairs = [];
		var key;
		var variables = this.getVariables();
		for(key in variables)
		{
			if(variables.hasOwnProperty(key))
			{
				variablePairs[variablePairs.length] = key +"="+ variables[key];
			}
		}
		return variablePairs;
	},
	getSWFHTML: function() {
		var swfNode = "";
		var params = {};
		var key = "";
		var pairs = "";
		if (navigator.plugins && navigator.mimeTypes && navigator.mimeTypes.length) { // netscape plugin architecture
			if (this.getAttribute("doExpressInstall")) {
				this.addVariable("MMplayerType", "PlugIn");
				this.setAttribute('swf', this.xiSWFPath);
			}
			swfNode = '<embed type="application/x-shockwave-flash" src="'+ this.getAttribute('swf') +'" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'" style="'+ this.getAttribute('style') +'"';
			swfNode += ' id="'+ this.getAttribute('id') +'" name="'+ this.getAttribute('id') +'" ';
			params = this.getParams();
			for(key in params)
			{
				if(params.hasOwnProperty(key))
				{
					swfNode += [key] +'="'+ params[key] +'" ';
				}
			}
			pairs = this.getVariablePairs().join("&");
			if (pairs.length > 0){ swfNode += 'flashvars="'+ pairs +'"'; }
			swfNode += '/>';
		} else { // PC IE
			if (this.getAttribute("doExpressInstall")) {
				this.addVariable("MMplayerType", "ActiveX");
				this.setAttribute('swf', this.xiSWFPath);
			}
			swfNode = '<object id="'+ this.getAttribute('id') +'" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'" style="'+ this.getAttribute('style') +'">';
			swfNode += '<param name="movie" value="'+ this.getAttribute('swf') +'" />';
			params = this.getParams();
			for(key in params)
			{
				if(params.hasOwnProperty(key))
				{
					swfNode += '<param name="'+ key +'" value="'+ params[key] +'" />';
				}
			}
			pairs = this.getVariablePairs().join("&");
			if(pairs.length > 0) {swfNode += '<param name="flashvars" value="'+ pairs +'" />';}
			swfNode += "</object>";
		}
		return swfNode;
	},
	write: function(elementId)
	{
		if(this.getAttribute('useExpressInstall')) {
			// check to see if we need to do an express install
			var expressInstallReqVer = new YAHOO.deconcept.PlayerVersion([6,0,65]);
			if (this.installedVer.versionIsValid(expressInstallReqVer) && !this.installedVer.versionIsValid(this.getAttribute('version'))) {
				this.setAttribute('doExpressInstall', true);
				this.addVariable("MMredirectURL", escape(this.getAttribute('xiRedirectUrl')));
				document.title = document.title.slice(0, 47) + " - Flash Player Installation";
				this.addVariable("MMdoctitle", document.title);
			}
		}
		if(this.skipDetect || this.getAttribute('doExpressInstall') || this.installedVer.versionIsValid(this.getAttribute('version')))
		{
			var n = (typeof elementId == 'string') ? document.getElementById(elementId) : elementId;
			n.innerHTML = this.getSWFHTML();
			return true;
		}
		else
		{
			if(this.getAttribute('redirectUrl') !== "")
			{
				document.location.replace(this.getAttribute('redirectUrl'));
			}
		}
		return false;
	}
};

/* ---- detection functions ---- */
YAHOO.deconcept.SWFObjectUtil.getPlayerVersion = function()
{
	var axo = null;
	var PlayerVersion = new YAHOO.deconcept.PlayerVersion([0,0,0]);
	if(navigator.plugins && navigator.mimeTypes.length)
	{
		var x = navigator.plugins["Shockwave Flash"];
		if(x && x.description)
		{
			PlayerVersion = new YAHOO.deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/, "").replace(/(\s+r|\s+b[0-9]+)/, ".").split("."));
		}
	}
	else if (navigator.userAgent && navigator.userAgent.indexOf("Windows CE") >= 0)
	{ // if Windows CE
		var counter = 3;
		while(axo)
		{
			try
			{
				counter++;
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+ counter);
//				document.write("player v: "+ counter);
				PlayerVersion = new YAHOO.deconcept.PlayerVersion([counter,0,0]);
			}
			catch(e)
			{
				axo = null;
			}
		}
	}
	else
	{ // Win IE (non mobile)
		// do minor version lookup in IE, but avoid fp6 crashing issues
		// see http://blog.deconcept.com/2006/01/11/getvariable-setvariable-crash-internet-explorer-flash-6/
		try
		{
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
		}
		catch(e)
		{
			try
			{
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
				PlayerVersion = new YAHOO.deconcept.PlayerVersion([6,0,21]);
				axo.AllowScriptAccess = "always"; // error if player version < 6.0.47 (thanks to Michael Williams @ Adobe for this code)
			}
			catch(e)
			{
				if(PlayerVersion.major == 6)
				{
					return PlayerVersion;
				}
			}
			try
			{
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
			}
			catch(e) {}
		}
		
		if(axo !== null)
		{
			PlayerVersion = new YAHOO.deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));
		}
	}
	return PlayerVersion;
};

YAHOO.deconcept.PlayerVersion = function(arrVersion)
{
	this.major = arrVersion[0] !== null ? parseInt(arrVersion[0], 0) : 0;
	this.minor = arrVersion[1] !== null ? parseInt(arrVersion[1], 0) : 0;
	this.rev = arrVersion[2] !== null ? parseInt(arrVersion[2], 0) : 0;
};

YAHOO.deconcept.PlayerVersion.prototype.versionIsValid = function(fv)
{
	if(this.major < fv.major)
	{
		return false;
	}
	if(this.major > fv.major)
	{
		return true;
	}
	if(this.minor < fv.minor)
	{
		return false;
	}
	if(this.minor > fv.minor)
	{
		return true;
	}
	if(this.rev < fv.rev)
	{
		return false;
	}
	return true;
};

/* ---- get value of query string param ---- */
YAHOO.deconcept.util =
{
	getRequestParameter: function(param)
	{
		var q = document.location.search || document.location.hash;
		if(param === null) { return q; }
		if(q)
		{
			var pairs = q.substring(1).split("&");
			for(var i=0; i < pairs.length; i++)
			{
				if (pairs[i].substring(0, pairs[i].indexOf("=")) == param)
				{
					return pairs[i].substring((pairs[i].indexOf("=") + 1));
				}
			}
		}
		return "";
	}
};

/* fix for video streaming bug */
YAHOO.deconcept.SWFObjectUtil.cleanupSWFs = function()
{
	var objects = document.getElementsByTagName("OBJECT");
	for(var i = objects.length - 1; i >= 0; i--)
	{
		objects[i].style.display = 'none';
		for(var x in objects[i])
		{
			if(typeof objects[i][x] == 'function')
			{
				objects[i][x] = function(){};
			}
		}
	}
};

// fixes bug in some fp9 versions see http://blog.deconcept.com/2006/07/28/swfobject-143-released/
if(YAHOO.deconcept.SWFObject.doPrepUnload)
{
	if(!YAHOO.deconcept.unloadSet)
	{
		YAHOO.deconcept.SWFObjectUtil.prepUnload = function()
		{
			__flash_unloadHandler = function(){};
			__flash_savedUnloadHandler = function(){};
			window.attachEvent("onunload", YAHOO.deconcept.SWFObjectUtil.cleanupSWFs);
		};
		window.attachEvent("onbeforeunload", YAHOO.deconcept.SWFObjectUtil.prepUnload);
		YAHOO.deconcept.unloadSet = true;
	}
}

/* add document.getElementById if needed (mobile IE < 5) */
if(!document.getElementById && document.all)
{
	document.getElementById = function(id) { return document.all[id]; };
}
/**
 * Wraps Flash embedding functionality and allows communication with SWF through
 * attributes.
 *
 * @namespace YAHOO.widget
 * @class FlashAdapter
 * @uses YAHOO.util.AttributeProvider
 */
YAHOO.widget.FlashAdapter = function(swfURL, containerID, attributes, buttonSkin)
{
	
	this._queue = this._queue || [];
	this._events = this._events || {};
	this._configs = this._configs || {};
	attributes = attributes || {};
	
	//the Flash Player external interface code from Adobe doesn't play nicely
	//with the default value, yui-gen, in IE
	this._id = attributes.id = attributes.id || YAHOO.util.Dom.generateId(null, "yuigen");
	attributes.version = attributes.version || "9.0.45";
	attributes.backgroundColor = attributes.backgroundColor || "#ffffff";
	
	//we can't use the initial attributes right away
	//so save them for once the SWF finishes loading
	this._attributes = attributes;
	
	this._swfURL = swfURL;
	this._containerID = containerID;
	
	//embed the SWF file in the page
	this._embedSWF(this._swfURL, this._containerID, attributes.id, attributes.version,
		attributes.backgroundColor, attributes.expressInstall, attributes.wmode, buttonSkin);
	
	/**
	 * Fires when the SWF is initialized and communication is possible.
	 * @event contentReady
	 */
	//Fix for iframe cross-domain issue with FF2x 
	try
	{
		this.createEvent("contentReady");
	}
	catch(e){}
};

YAHOO.widget.FlashAdapter.owners = YAHOO.widget.FlashAdapter.owners || {};

YAHOO.extend(YAHOO.widget.FlashAdapter, YAHOO.util.AttributeProvider,
{
	/**
	 * The URL of the SWF file.
	 * @property _swfURL
	 * @type String
	 * @private
	 */
	_swfURL: null,

	/**
	 * The ID of the containing DIV.
	 * @property _containerID
	 * @type String
	 * @private
	 */
	_containerID: null,

	/**
	 * A reference to the embedded SWF file.
	 * @property _swf
	 * @private
	 */
	_swf: null,

	/**
	 * The id of this instance.
	 * @property _id
	 * @type String
	 * @private
	 */
	_id: null,

	/**
	 * Indicates whether the SWF has been initialized and is ready
	 * to communicate with JavaScript
	 * @property _initialized
	 * @type Boolean
	 * @private
	 */
	_initialized: false,
	
	/**
	 * The initializing attributes are stored here until the SWF is ready.
	 * @property _attributes
	 * @type Object
	 * @private
	 */
	_attributes: null, //the intializing attributes

	/**
	 * Public accessor to the unique name of the FlashAdapter instance.
	 *
	 * @method toString
	 * @return {String} Unique name of the FlashAdapter instance.
	 */
	toString: function()
	{
		return "FlashAdapter " + this._id;
	},

	/**
	 * Nulls out the entire FlashAdapter instance and related objects and removes attached
	 * event listeners and clears out DOM elements inside the container. After calling
	 * this method, the instance reference should be expliclitly nulled by implementer,
	 * as in myChart = null. Use with caution!
	 *
	 * @method destroy
	 */
	destroy: function()
	{
		//kill the Flash Player instance
		if(this._swf)
		{
			var container = YAHOO.util.Dom.get(this._containerID);
			container.removeChild(this._swf);
		}
		
		var instanceName = this._id;
		
		//null out properties
		for(var prop in this)
		{
			if(YAHOO.lang.hasOwnProperty(this, prop))
			{
				this[prop] = null;
			}
		}
		
	},

	/**
	 * Embeds the SWF in the page and associates it with this instance.
	 *
	 * @method _embedSWF
	 * @private
	 */
	_embedSWF: function(swfURL, containerID, swfID, version, backgroundColor, expressInstall, wmode, buttonSkin)
	{
		//standard SWFObject embed
		var swfObj = new YAHOO.deconcept.SWFObject(swfURL, swfID, "100%", "100%", version, backgroundColor);

		if(expressInstall)
		{
			swfObj.useExpressInstall(expressInstall);
		}

		//make sure we can communicate with ExternalInterface
		swfObj.addParam("allowScriptAccess", "always");
		
		if(wmode)
		{
			swfObj.addParam("wmode", wmode);
		}
		
		swfObj.addParam("menu", "false");
		
		//again, a useful ExternalInterface trick
		swfObj.addVariable("allowedDomain", document.location.hostname);

		//tell the SWF which HTML element it is in
		swfObj.addVariable("YUISwfId", swfID);

		// set the name of the function to call when the swf has an event
		swfObj.addVariable("YUIBridgeCallback", "YAHOO.widget.FlashAdapter.eventHandler");
		if (buttonSkin) {
		swfObj.addVariable("buttonSkin", buttonSkin);
		}
		var container = YAHOO.util.Dom.get(containerID);
		var result = swfObj.write(container);
		if(result)
		{
			this._swf = YAHOO.util.Dom.get(swfID);
			YAHOO.widget.FlashAdapter.owners[swfID] = this;
		}
		else
		{
		}
	},

	/**
	 * Handles or re-dispatches events received from the SWF.
	 *
	 * @method _eventHandler
	 * @private
	 */
	_eventHandler: function(event)
	{
		var type = event.type;
		switch(type)
		{
			case "swfReady":
   				this._loadHandler();
				return;
			case "log":
				return;
		}
		
		
		//be sure to return after your case or the event will automatically fire!
		this.fireEvent(type, event);
	},

	/**
	 * Called when the SWF has been initialized.
	 *
	 * @method _loadHandler
	 * @private
	 */
	_loadHandler: function()
	{
		this._initialized = false;
		this._initAttributes(this._attributes);
		this.setAttributes(this._attributes, true);
		
		this._initialized = true;
		this.fireEvent("contentReady");
	},
	
	set: function(name, value)
	{
		//save all the attributes in case the swf reloads
		//so that we can pass them in again
		this._attributes[name] = value;
		YAHOO.widget.FlashAdapter.superclass.set.call(this, name, value);
	},
	
	/**
	 * Initializes the attributes.
	 *
	 * @method _initAttributes
	 * @private
	 */
	_initAttributes: function(attributes)
	{
		//should be overridden if other attributes need to be set up

		/**
		 * @attribute wmode
		 * @description Sets the window mode of the Flash Player control. May be
		 *		"window", "opaque", or "transparent". Only available in the constructor
		 *		because it may not be set after Flash Player has been embedded in the page.
		 * @type String
		 */
		 
		/**
		 * @attribute expressInstall
		 * @description URL pointing to a SWF file that handles Flash Player's express
		 *		install feature. Only available in the constructor because it may not be
		 *		set after Flash Player has been embedded in the page.
		 * @type String
		 */

		/**
		 * @attribute version
		 * @description Minimum required version for the SWF file. Only available in the constructor because it may not be
		 *		set after Flash Player has been embedded in the page.
		 * @type String
		 */

		/**
		 * @attribute backgroundColor
		 * @description The background color of the SWF. Only available in the constructor because it may not be
		 *		set after Flash Player has been embedded in the page.
		 * @type String
		 */
		 
		/**
		 * @attribute altText
		 * @description The alternative text to provide for screen readers and other assistive technology.
		 * @type String
		 */
		this.getAttributeConfig("altText",
		{
			method: this._getAltText
		});
		this.setAttributeConfig("altText",
		{
			method: this._setAltText
		});
		
		/**
		 * @attribute swfURL
		 * @description Absolute or relative URL to the SWF displayed by the FlashAdapter. Only available in the constructor because it may not be
		 *		set after Flash Player has been embedded in the page.
		 * @type String
		 */
		this.getAttributeConfig("swfURL",
		{
			method: this._getSWFURL
		});
	},
	
	/**
	 * Getter for swfURL attribute.
	 *
	 * @method _getSWFURL
	 * @private
	 */
	_getSWFURL: function()
	{
		return this._swfURL;
	},
	
	/**
	 * Getter for altText attribute.
	 *
	 * @method _getAltText
	 * @private
	 */
	_getAltText: function()
	{
		return this._swf.getAltText();
	},

	/**
	 * Setter for altText attribute.
	 *
	 * @method _setAltText
	 * @private
	 */
	_setAltText: function(value)
	{
		return this._swf.setAltText(value);
	}
});


/**
 * Receives event messages from SWF and passes them to the correct instance
 * of FlashAdapter.
 *
 * @method YAHOO.widget.FlashAdapter.eventHandler
 * @static
 * @private
 */
YAHOO.widget.FlashAdapter.eventHandler = function(elementID, event)
{

	if(!YAHOO.widget.FlashAdapter.owners[elementID])
	{
		//fix for ie: if owner doesn't exist yet, try again in a moment
		setTimeout(function() { YAHOO.widget.FlashAdapter.eventHandler( elementID, event ); }, 0);
	}
	else
	{
		YAHOO.widget.FlashAdapter.owners[elementID]._eventHandler(event);
	}
};

/**
 * The number of proxy functions that have been created.
 * @static
 * @private
 */
YAHOO.widget.FlashAdapter.proxyFunctionCount = 0;

/**
 * Creates a globally accessible function that wraps a function reference.
 * Returns the proxy function's name as a string for use by the SWF through
 * ExternalInterface.
 *
 * @method YAHOO.widget.FlashAdapter.createProxyFunction
 * @static
 * @private
 */
YAHOO.widget.FlashAdapter.createProxyFunction = function(func)
{
	var index = YAHOO.widget.FlashAdapter.proxyFunctionCount;
	YAHOO.widget.FlashAdapter["proxyFunction" + index] = function()
	{
		return func.apply(null, arguments);
	};
	YAHOO.widget.FlashAdapter.proxyFunctionCount++;
	return "YAHOO.widget.FlashAdapter.proxyFunction" + index.toString();
};

/**
 * Removes a function created with createProxyFunction()
 * 
 * @method YAHOO.widget.FlashAdapter.removeProxyFunction
 * @static
 * @private
 */
YAHOO.widget.FlashAdapter.removeProxyFunction = function(funcName)
{
	//quick error check
	if(!funcName || funcName.indexOf("YAHOO.widget.FlashAdapter.proxyFunction") < 0)
	{
		return;
	}
	
	funcName = funcName.substr(26);
	YAHOO.widget.FlashAdapter[funcName] = null;
};
/**
 * The YUI Uploader Control
 * @module uploader
 * @description <p>YUI Uploader provides file upload functionality that goes beyond the basic browser-based methods. 
 * Specifically, the YUI Uploader allows for:
 * <ol>
 * <li> Multiple file selection in a single "Open File" dialog.</li>
 * <li> File extension filters to facilitate the user's selection.</li>
 * <li> Progress tracking for file uploads.</li>
 * <li> A range of file metadata: filename, size, date created, date modified, and author.</li>
 * <li> A set of events dispatched on various aspects of the file upload process: file selection, upload progress, upload completion, etc.</li>
 * <li> Inclusion of additional data in the file upload POST request.</li>
 * <li> Faster file upload on broadband connections due to the modified SEND buffer size.</li>
 * <li> Same-page server response upon completion of the file upload.</li>
 * </ol>
 * </p>
 * @title Uploader
 * @namespace YAHOO.widget
 * @requires yahoo, dom, element, event
 */
/**
 * Uploader class for the YUI Uploader component.
 *
 * @namespace YAHOO.widget
 * @class Uploader
 * @uses YAHOO.widget.FlashAdapter
 * @constructor
 * @param containerId {HTMLElement} Container element for the Flash Player instance.
 * @param buttonSkin {String} [optional]. If defined, the uploader is 
 * rendered as a button. This parameter must provide the URL of a button
 * skin sprite image. Acceptable types are: jpg, gif, png and swf. The 
 * sprite is divided evenly into four sections along its height (e.g., if
 * the sprite is 200 px tall, it's divided into four sections 50px each).
 * Each section is used as a skin for a specific state of the button: top
 * section is "up", second section is "over", third section is "down", and
 * fourth section is "disabled". 
 * If the parameter is not supplied, the uploader is rendered transparent,
 * and it's the developer's responsibility to create a visible UI below it.
 * @param forceTransparent {Boolean} This parameter, if true, forces the Flash
 * UI to be rendered with wmode set to "transparent". This behavior is useful 
 * in conjunction with non-rectangular button skins with PNG transparency. 
 * The parameter is false by default, and ignored if no buttonSkin is defined.
  */
YAHOO.widget.Uploader = function(containerId, buttonSkin, forceTransparent)
{
	var newWMode = "window";

	if (!(buttonSkin) || (buttonSkin && forceTransparent)) {
		newWMode = "transparent";
	}

	
 	YAHOO.widget.Uploader.superclass.constructor.call(this, YAHOO.widget.Uploader.SWFURL, containerId, {wmode:newWMode}, buttonSkin);

    if(!this._swf)
	{
       alert("浏览器没有安装Flash插件，不能执行上传操作");
       return;
    } 
	/**
	 * Fires when the mouse is pressed over the Uploader.
	 * Only fires when the Uploader UI is enabled and
	 * the render type is 'transparent'.
	 *
	 * @event mouseDown
	 * @param event.type {String} The event type
	 */
	this.createEvent("mouseDown");
	
	/**
	 * Fires when the mouse is released over the Uploader.
	 * Only fires when the Uploader UI is enabled and
	 * the render type is 'transparent'.
	 *
	 * @event mouseUp
	 * @param event.type {String} The event type
	 */
	this.createEvent("mouseUp");

	/**
	 * Fires when the mouse rolls over the Uploader.
	 *
	 * @event rollOver
	 * @param event.type {String} The event type
	 */
	this.createEvent("rollOver");
	
	/**
	 * Fires when the mouse rolls out of the Uploader.
	 *
	 * @event rollOut
	 * @param event.type {String} The event type
	 */
	this.createEvent("rollOut");
	
	/**
	 * Fires when the uploader is clicked.
	 *
	 * @event click
	 * @param event.type {String} The event type
	 */
	this.createEvent("click");
	
	/**
	 * Fires when the user has finished selecting files in the "Open File" dialog.
	 *
	 * @event fileSelect
	 * @param event.type {String} The event type
	 * @param event.fileList {Object} A dictionary of objects with file information
	 * @param event.fileList[].size {Number} File size in bytes for a specific file in fileList
	 * @param event.fileList[].cDate {Date} Creation date for a specific file in fileList
	 * @param event.fileList[].mDate {Date} Modification date for a specific file in fileList
	 * @param event.fileList[].name {String} File name for a specific file in fileList
	 * @param event.fileList[].id {String} Unique file id of a specific file in fileList
	 */
	this.createEvent("fileSelect");

	/**
	 * Fires when an upload of a specific file has started.
	 *
	 * @event uploadStart
	 * @param event.type {String} The event type
	 * @param event.id {String} The id of the file that's started to upload
	 */
	this.createEvent("uploadStart");

	/**
	 * Fires when new information about the upload progress for a specific file is available.
	 *
	 * @event uploadProgress
	 * @param event.type {String} The event type
	 * @param event.id {String} The id of the file with which the upload progress data is associated
	 * @param bytesLoaded {Number} The number of bytes of the file uploaded so far
	 * @param bytesTotal {Number} The total size of the file
	 */
	this.createEvent("uploadProgress");
	
	/**
	 * Fires when an upload for a specific file is cancelled.
	 *
	 * @event uploadCancel
	 * @param event.type {String} The event type
	 * @param event.id {String} The id of the file with which the upload has been cancelled.
	 */	
	this.createEvent("uploadCancel");

	/**
	 * Fires when an upload for a specific file is complete.
	 *
	 * @event uploadComplete
	 * @param event.type {String} The event type
	 * @param event.id {String} The id of the file for which the upload has been completed.
	 */	
	this.createEvent("uploadComplete");

	/**
	 * Fires when the server sends data in response to a completed upload.
	 *
	 * @event uploadCompleteData
	 * @param event.type {String} The event type
	 * @param event.id {String} The id of the file for which the upload has been completed.
	 * @param event.data {String} The raw data returned by the server in response to the upload.
	 */	
	this.createEvent("uploadCompleteData");
	
	/**
	 * Fires when an upload error occurs.
	 *
	 * @event uploadError
	 * @param event.type {String} The event type
	 * @param event.id {String} The id of the file that was being uploaded when the error has occurred.
	 * @param event.status {String} The status message associated with the error.
	 */	
	this.createEvent("uploadError");

	/* WSH Added */
	this.createEvent("commitResponse");
	this.createEvent("commitError");
	/* Added End */
}

/**
 * Location of the Uploader SWF
 *
 * @property Chart.SWFURL
 * @private
 * @static
 * @final
 * @default "assets/uploader.swf"
 */
YAHOO.widget.Uploader.SWFURL = "assets/uploader.swf";

YAHOO.extend(YAHOO.widget.Uploader, YAHOO.widget.FlashAdapter,
{	
/**
 * Starts the upload of the file specified by fileID to the location specified by uploadScriptPath.
 *
 * @param fileID {String} The id of the file to start uploading.
 * @param uploadScriptPath {String} The URL of the upload location.
 * @param method {String} Either "GET" or "POST", specifying how the variables accompanying the file upload POST request should be submitted. "GET" by default.
 * @param vars {Object} The object containing variables to be sent in the same request as the file upload.
 * @param fieldName {String} The name of the variable in the POST request containing the file data. "Filedata" by default.
 * </code> 
 */
	upload: function(fileID, uploadScriptPath, method, vars, fieldName)
	{
		this._swf.upload(fileID, uploadScriptPath, method, vars, fieldName);
	},
	
/**
 * Starts the upload of the files specified by fileIDs, or adds them to a currently running queue. The upload queue is automatically managed.
 *
 * @param fileIDs {Array} The ids of the files to start uploading.
 * @param uploadScriptPath {String} The URL of the upload location.
 * @param method {String} Either "GET" or "POST", specifying how the variables accompanying the file upload POST request should be submitted. "GET" by default.
 * @param vars {Object} The object containing variables to be sent in the same request as the file upload.
 * @param fieldName {String} The name of the variable in the POST request containing the file data. "Filedata" by default.
 * </code> 
 */
	uploadThese: function(fileIDs, uploadScriptPath, method, vars, fieldName)
	{
		this._swf.uploadThese(fileIDs, uploadScriptPath, method, vars, fieldName);
	},
	
/**
 * Starts uploading all files in the queue. If this function is called, the upload queue is automatically managed.
 *
 * @param uploadScriptPath {String} The URL of the upload location.
 * @param method {String} Either "GET" or "POST", specifying how the variables accompanying the file upload POST request should be submitted. "GET" by default.
 * @param vars {Object} The object containing variables to be sent in the same request as the file upload.
 * @param fieldName {String} The name of the variable in the POST request containing the file data. "Filedata" by default.
 * </code> 
 */
	uploadAll: function(uploadScriptPath, method, vars, fieldName)
	{
		this._swf.uploadAll(uploadScriptPath, method, vars, fieldName);
	},
	// 加载应用自己的安全策略 added by slm
	loadAppPolicy : function(appPath) 
	{
		this._swf.loadAppPolicy(appPath);
	},

/**
 * Cancels the upload of a specified file. If no file id is specified, all ongoing uploads are cancelled.
 *
 * @param fileID {String} The ID of the file whose upload should be cancelled.
 */
	cancel: function(fileID)
	{
		this._swf.cancel(fileID);
	},

/**
 * Clears the list of files queued for upload.
 *
 */
	clearFileList: function()
	{
		this._swf.clearFileList();
	},
	
/**
 * Removes the specified file from the upload queue. 
 *
 * @param fileID {String} The id of the file to remove from the upload queue. 
 */
	removeFile: function (fileID) 
	{
		this._swf.removeFile(fileID);
	},

/**
 * Turns the logging functionality on.
 * Uses Flash internal trace logging, as well as YUI Logger, if available.
 *
 * @param allowLogging {Boolean} If true, logs are output; otherwise, no logs are produced.
 */
    setAllowLogging: function (allowLogging)
    {
      	this._swf.setAllowLogging(allowLogging);
    },

/**
 * Sets the number of simultaneous uploads when using uploadAll()
 * The minimum value is 1, and maximum value is 5. The default value is 2.
 *
 * @param simUploadLimit {int} Number of simultaneous uploads, between 1 and 5.
 */
    setSimUploadLimit : function (simUploadLimit)
    {
       this._swf.setSimUploadLimit(simUploadLimit);
    },

/**
 * Sets the flag allowing users to select multiple files for the upload.
 *
 * @param allowMultipleFiles {Boolean} If true, multiple files can be selected. False by default.
 */     
    setAllowMultipleFiles : function (allowMultipleFiles) 
    {
       this._swf.setAllowMultipleFiles(allowMultipleFiles);
    },

/**
 * Sets the file filters for the "Browse" dialog.
 *
 *  @param newFilterArray An array of sets of key-value pairs of the form
 *  {extensions: extensionString, description: descriptionString, [optional]macType: macTypeString}
 *  The extensions string is a semicolon-delimited list of elements of the form "*.xxx", 
 *  e.g. "*.jpg;*.gif;*.png". 
 */       
    setFileFilters : function (fileFilters) 
    {
       this._swf.setFileFilters(fileFilters);
    },

	/**
	 * Enables the mouse events on the Uploader.
	 * If the uploader is being rendered as a button,
	 * then the button's skin is set to "up"
	 * (first section of the button skin sprite).
	 *
	 */
	enable : function ()
	{
		this._swf.enable();
	},

	/**
	 * Disables the mouse events on the Uploader.
	 * If the uploader is being rendered as a button,
	 * then the button's skin is set to "disabled"
	 * (fourth section of the button skin sprite).
	 *
	 */
	disable : function () 
	{
		this._swf.disable();
	}
});
YAHOO.register("uploader", YAHOO.widget.Uploader, {version: "2.8.2r1", build: "7"});

