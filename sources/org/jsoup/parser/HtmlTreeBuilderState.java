package org.jsoup.parser;

import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Token;

enum HtmlTreeBuilderState {
    Initial {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (token.isDoctype()) {
                Token.Doctype asDoctype = token.asDoctype();
                DocumentType documentType = new DocumentType(htmlTreeBuilder.settings.normalizeTag(asDoctype.getName()), asDoctype.getPublicIdentifier(), asDoctype.getSystemIdentifier());
                documentType.setPubSysKey(asDoctype.getPubSysKey());
                htmlTreeBuilder.getDocument().appendChild(documentType);
                if (asDoctype.isForceQuirks()) {
                    htmlTreeBuilder.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHtml);
            } else {
                htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHtml);
                return htmlTreeBuilder.process(token);
            }
            return true;
        }
    },
    BeforeHtml {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.insertStartTag("html");
            htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHead);
            return htmlTreeBuilder.process(token);
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            } else {
                if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                    htmlTreeBuilder.insert(token.asStartTag());
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHead);
                } else if (token.isEndTag() && StringUtil.in(token.asEndTag().normalName(), "head", "body", "html", "br")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    if (!token.isEndTag()) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
            return true;
        }
    },
    BeforeHead {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return HtmlTreeBuilderState.InBody.process(token, htmlTreeBuilder);
            } else {
                if (token.isStartTag() && token.asStartTag().normalName().equals("head")) {
                    htmlTreeBuilder.setHeadElement(htmlTreeBuilder.insert(token.asStartTag()));
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InHead);
                } else if (token.isEndTag() && StringUtil.in(token.asEndTag().normalName(), "head", "body", "html", "br")) {
                    htmlTreeBuilder.processStartTag("head");
                    return htmlTreeBuilder.process(token);
                } else if (token.isEndTag()) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    htmlTreeBuilder.processStartTag("head");
                    return htmlTreeBuilder.process(token);
                }
            }
            return true;
        }
    },
    InHead {
        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            treeBuilder.processEndTag("head");
            return treeBuilder.process(token);
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            int i = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i == 1) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (i == 2) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (i == 3) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("html")) {
                    return HtmlTreeBuilderState.InBody.process(token, htmlTreeBuilder);
                }
                if (StringUtil.in(normalName, "base", "basefont", "bgsound", "command", "link")) {
                    Element insertEmpty = htmlTreeBuilder.insertEmpty(asStartTag);
                    if (normalName.equals("base") && insertEmpty.hasAttr("href")) {
                        htmlTreeBuilder.maybeSetBaseUri(insertEmpty);
                    }
                } else if (normalName.equals("meta")) {
                    htmlTreeBuilder.insertEmpty(asStartTag);
                } else if (normalName.equals("title")) {
                    HtmlTreeBuilderState.handleRcData(asStartTag, htmlTreeBuilder);
                } else if (StringUtil.in(normalName, "noframes", "style")) {
                    HtmlTreeBuilderState.handleRawtext(asStartTag, htmlTreeBuilder);
                } else if (normalName.equals("noscript")) {
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InHeadNoscript);
                } else if (normalName.equals("script")) {
                    htmlTreeBuilder.tokeniser.transition(TokeniserState.ScriptData);
                    htmlTreeBuilder.markInsertionMode();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.Text);
                    htmlTreeBuilder.insert(asStartTag);
                } else if (!normalName.equals("head")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            } else if (i != 4) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                String normalName2 = token.asEndTag().normalName();
                if (normalName2.equals("head")) {
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterHead);
                } else if (StringUtil.in(normalName2, "body", "html", "br")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
            return true;
        }
    },
    InHeadNoscript {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.insert(new Token.Character().data(token.toString()));
            return true;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return true;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            } else {
                if (token.isEndTag() && token.asEndTag().normalName().equals("noscript")) {
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InHead);
                    return true;
                } else if (HtmlTreeBuilderState.isWhitespace(token) || token.isComment() || (token.isStartTag() && StringUtil.in(token.asStartTag().normalName(), "basefont", "bgsound", "link", "meta", "noframes", "style"))) {
                    return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
                } else {
                    if (token.isEndTag() && token.asEndTag().normalName().equals("br")) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    if ((!token.isStartTag() || !StringUtil.in(token.asStartTag().normalName(), "head", "noscript")) && !token.isEndTag()) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
        }
    },
    AfterHead {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.processStartTag("body");
            htmlTreeBuilder.framesetOk(true);
            return htmlTreeBuilder.process(token);
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            Token token2 = token;
            HtmlTreeBuilder htmlTreeBuilder2 = htmlTreeBuilder;
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder2.insert(token.asCharacter());
                return true;
            } else if (token.isComment()) {
                htmlTreeBuilder2.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder2.error(this);
                return true;
            } else if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("html")) {
                    return htmlTreeBuilder2.process(token2, HtmlTreeBuilderState.InBody);
                }
                if (normalName.equals("body")) {
                    htmlTreeBuilder2.insert(asStartTag);
                    htmlTreeBuilder2.framesetOk(false);
                    htmlTreeBuilder2.transition(HtmlTreeBuilderState.InBody);
                    return true;
                } else if (normalName.equals("frameset")) {
                    htmlTreeBuilder2.insert(asStartTag);
                    htmlTreeBuilder2.transition(HtmlTreeBuilderState.InFrameset);
                    return true;
                } else if (StringUtil.in(normalName, "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "title")) {
                    htmlTreeBuilder2.error(this);
                    Element headElement = htmlTreeBuilder.getHeadElement();
                    htmlTreeBuilder2.push(headElement);
                    htmlTreeBuilder2.process(token2, HtmlTreeBuilderState.InHead);
                    htmlTreeBuilder2.removeFromStack(headElement);
                    return true;
                } else if (normalName.equals("head")) {
                    htmlTreeBuilder2.error(this);
                    return false;
                } else {
                    anythingElse(token, htmlTreeBuilder);
                    return true;
                }
            } else if (!token.isEndTag()) {
                anythingElse(token, htmlTreeBuilder);
                return true;
            } else if (StringUtil.in(token.asEndTag().normalName(), "body", "html")) {
                anythingElse(token, htmlTreeBuilder);
                return true;
            } else {
                htmlTreeBuilder2.error(this);
                return false;
            }
        }
    },
    InBody {
        public boolean anyOtherEndTag(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            String normalizeTag = htmlTreeBuilder.settings.normalizeTag(token.asEndTag().name());
            ArrayList<Element> stack = htmlTreeBuilder.getStack();
            int size = stack.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                Element element = stack.get(size);
                if (element.nodeName().equals(normalizeTag)) {
                    htmlTreeBuilder.generateImpliedEndTags(normalizeTag);
                    if (!normalizeTag.equals(htmlTreeBuilder.currentElement().nodeName())) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose(normalizeTag);
                } else if (htmlTreeBuilder.isSpecial(element)) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    size--;
                }
            }
            return true;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            Element element;
            Token token2 = token;
            HtmlTreeBuilder htmlTreeBuilder2 = htmlTreeBuilder;
            int i = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token2.type.ordinal()];
            boolean z = true;
            if (i == 1) {
                htmlTreeBuilder2.insert(token.asComment());
            } else if (i == 2) {
                htmlTreeBuilder2.error(this);
                return false;
            } else if (i == 3) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("a")) {
                    if (htmlTreeBuilder2.getActiveFormattingElement("a") != null) {
                        htmlTreeBuilder2.error(this);
                        htmlTreeBuilder2.processEndTag("a");
                        Element fromStack = htmlTreeBuilder2.getFromStack("a");
                        if (fromStack != null) {
                            htmlTreeBuilder2.removeFromActiveFormattingElements(fromStack);
                            htmlTreeBuilder2.removeFromStack(fromStack);
                        }
                    }
                    htmlTreeBuilder.reconstructFormattingElements();
                    htmlTreeBuilder2.pushActiveFormattingElements(htmlTreeBuilder2.insert(asStartTag));
                } else if (StringUtil.inSorted(normalName, Constants.InBodyStartEmptyFormatters)) {
                    htmlTreeBuilder.reconstructFormattingElements();
                    htmlTreeBuilder2.insertEmpty(asStartTag);
                    htmlTreeBuilder2.framesetOk(false);
                } else if (StringUtil.inSorted(normalName, Constants.InBodyStartPClosers)) {
                    if (htmlTreeBuilder2.inButtonScope("p")) {
                        htmlTreeBuilder2.processEndTag("p");
                    }
                    htmlTreeBuilder2.insert(asStartTag);
                } else if (normalName.equals("span")) {
                    htmlTreeBuilder.reconstructFormattingElements();
                    htmlTreeBuilder2.insert(asStartTag);
                } else if (normalName.equals("li")) {
                    htmlTreeBuilder2.framesetOk(false);
                    ArrayList<Element> stack = htmlTreeBuilder.getStack();
                    int size = stack.size() - 1;
                    while (true) {
                        if (size <= 0) {
                            break;
                        }
                        Element element2 = stack.get(size);
                        if (!element2.nodeName().equals("li")) {
                            if (htmlTreeBuilder2.isSpecial(element2) && !StringUtil.inSorted(element2.nodeName(), Constants.InBodyStartLiBreakers)) {
                                break;
                            }
                            size--;
                        } else {
                            htmlTreeBuilder2.processEndTag("li");
                            break;
                        }
                    }
                    if (htmlTreeBuilder2.inButtonScope("p")) {
                        htmlTreeBuilder2.processEndTag("p");
                    }
                    htmlTreeBuilder2.insert(asStartTag);
                } else if (normalName.equals("html")) {
                    htmlTreeBuilder2.error(this);
                    Element element3 = htmlTreeBuilder.getStack().get(0);
                    Iterator<Attribute> it = asStartTag.getAttributes().iterator();
                    while (it.hasNext()) {
                        Attribute next = it.next();
                        if (!element3.hasAttr(next.getKey())) {
                            element3.attributes().put(next);
                        }
                    }
                } else if (StringUtil.inSorted(normalName, Constants.InBodyStartToHead)) {
                    return htmlTreeBuilder2.process(token2, HtmlTreeBuilderState.InHead);
                } else {
                    if (normalName.equals("body")) {
                        htmlTreeBuilder2.error(this);
                        ArrayList<Element> stack2 = htmlTreeBuilder.getStack();
                        if (stack2.size() == 1 || (stack2.size() > 2 && !stack2.get(1).nodeName().equals("body"))) {
                            return false;
                        }
                        htmlTreeBuilder2.framesetOk(false);
                        Element element4 = stack2.get(1);
                        Iterator<Attribute> it2 = asStartTag.getAttributes().iterator();
                        while (it2.hasNext()) {
                            Attribute next2 = it2.next();
                            if (!element4.hasAttr(next2.getKey())) {
                                element4.attributes().put(next2);
                            }
                        }
                    } else if (normalName.equals("frameset")) {
                        htmlTreeBuilder2.error(this);
                        ArrayList<Element> stack3 = htmlTreeBuilder.getStack();
                        if (stack3.size() == 1 || ((stack3.size() > 2 && !stack3.get(1).nodeName().equals("body")) || !htmlTreeBuilder.framesetOk())) {
                            return false;
                        }
                        Element element5 = stack3.get(1);
                        if (element5.parent() != null) {
                            element5.remove();
                        }
                        for (int i2 = 1; stack3.size() > i2; i2 = 1) {
                            stack3.remove(stack3.size() - i2);
                        }
                        htmlTreeBuilder2.insert(asStartTag);
                        htmlTreeBuilder2.transition(HtmlTreeBuilderState.InFrameset);
                    } else {
                        String[] strArr = Constants.Headings;
                        if (StringUtil.inSorted(normalName, strArr)) {
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            if (StringUtil.inSorted(htmlTreeBuilder.currentElement().nodeName(), strArr)) {
                                htmlTreeBuilder2.error(this);
                                htmlTreeBuilder.pop();
                            }
                            htmlTreeBuilder2.insert(asStartTag);
                        } else if (StringUtil.inSorted(normalName, Constants.InBodyStartPreListing)) {
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder2.insert(asStartTag);
                            htmlTreeBuilder2.reader.matchConsume("\n");
                            htmlTreeBuilder2.framesetOk(false);
                        } else if (normalName.equals("form")) {
                            if (htmlTreeBuilder.getFormElement() != null) {
                                htmlTreeBuilder2.error(this);
                                return false;
                            }
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder2.insertForm(asStartTag, true);
                            return true;
                        } else if (StringUtil.inSorted(normalName, Constants.DdDt)) {
                            htmlTreeBuilder2.framesetOk(false);
                            ArrayList<Element> stack4 = htmlTreeBuilder.getStack();
                            int size2 = stack4.size() - 1;
                            while (true) {
                                if (size2 <= 0) {
                                    break;
                                }
                                Element element6 = stack4.get(size2);
                                if (!StringUtil.inSorted(element6.nodeName(), Constants.DdDt)) {
                                    if (htmlTreeBuilder2.isSpecial(element6) && !StringUtil.inSorted(element6.nodeName(), Constants.InBodyStartLiBreakers)) {
                                        break;
                                    }
                                    size2--;
                                } else {
                                    htmlTreeBuilder2.processEndTag(element6.nodeName());
                                    break;
                                }
                            }
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder2.insert(asStartTag);
                        } else if (normalName.equals("plaintext")) {
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder2.insert(asStartTag);
                            htmlTreeBuilder2.tokeniser.transition(TokeniserState.PLAINTEXT);
                        } else if (normalName.equals("button")) {
                            if (htmlTreeBuilder2.inButtonScope("button")) {
                                htmlTreeBuilder2.error(this);
                                htmlTreeBuilder2.processEndTag("button");
                                htmlTreeBuilder2.process(asStartTag);
                            } else {
                                htmlTreeBuilder.reconstructFormattingElements();
                                htmlTreeBuilder2.insert(asStartTag);
                                htmlTreeBuilder2.framesetOk(false);
                            }
                        } else if (StringUtil.inSorted(normalName, Constants.Formatters)) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.pushActiveFormattingElements(htmlTreeBuilder2.insert(asStartTag));
                        } else if (normalName.equals("nobr")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            if (htmlTreeBuilder2.inScope("nobr")) {
                                htmlTreeBuilder2.error(this);
                                htmlTreeBuilder2.processEndTag("nobr");
                                htmlTreeBuilder.reconstructFormattingElements();
                            }
                            htmlTreeBuilder2.pushActiveFormattingElements(htmlTreeBuilder2.insert(asStartTag));
                        } else if (StringUtil.inSorted(normalName, Constants.InBodyStartApplets)) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.insert(asStartTag);
                            htmlTreeBuilder.insertMarkerToFormattingElements();
                            htmlTreeBuilder2.framesetOk(false);
                        } else if (normalName.equals("table")) {
                            if (htmlTreeBuilder.getDocument().quirksMode() != Document.QuirksMode.quirks && htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder2.insert(asStartTag);
                            htmlTreeBuilder2.framesetOk(false);
                            htmlTreeBuilder2.transition(HtmlTreeBuilderState.InTable);
                        } else if (normalName.equals("input")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            if (!htmlTreeBuilder2.insertEmpty(asStartTag).attr("type").equalsIgnoreCase("hidden")) {
                                htmlTreeBuilder2.framesetOk(false);
                            }
                        } else if (StringUtil.inSorted(normalName, Constants.InBodyStartMedia)) {
                            htmlTreeBuilder2.insertEmpty(asStartTag);
                        } else if (normalName.equals("hr")) {
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder2.insertEmpty(asStartTag);
                            htmlTreeBuilder2.framesetOk(false);
                        } else if (normalName.equals("image")) {
                            if (htmlTreeBuilder2.getFromStack("svg") == null) {
                                return htmlTreeBuilder2.process(asStartTag.name("img"));
                            }
                            htmlTreeBuilder2.insert(asStartTag);
                        } else if (normalName.equals("isindex")) {
                            htmlTreeBuilder2.error(this);
                            if (htmlTreeBuilder.getFormElement() != null) {
                                return false;
                            }
                            htmlTreeBuilder2.processStartTag("form");
                            if (asStartTag.attributes.hasKey("action")) {
                                htmlTreeBuilder.getFormElement().attr("action", asStartTag.attributes.get("action"));
                            }
                            htmlTreeBuilder2.processStartTag("hr");
                            htmlTreeBuilder2.processStartTag("label");
                            htmlTreeBuilder2.process(new Token.Character().data(asStartTag.attributes.hasKey("prompt") ? asStartTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: "));
                            Attributes attributes = new Attributes();
                            Iterator<Attribute> it3 = asStartTag.attributes.iterator();
                            while (it3.hasNext()) {
                                Attribute next3 = it3.next();
                                if (!StringUtil.inSorted(next3.getKey(), Constants.InBodyStartInputAttribs)) {
                                    attributes.put(next3);
                                }
                            }
                            attributes.put("name", "isindex");
                            htmlTreeBuilder2.processStartTag("input", attributes);
                            htmlTreeBuilder2.processEndTag("label");
                            htmlTreeBuilder2.processStartTag("hr");
                            htmlTreeBuilder2.processEndTag("form");
                        } else if (normalName.equals("textarea")) {
                            htmlTreeBuilder2.insert(asStartTag);
                            htmlTreeBuilder2.tokeniser.transition(TokeniserState.Rcdata);
                            htmlTreeBuilder.markInsertionMode();
                            htmlTreeBuilder2.framesetOk(false);
                            htmlTreeBuilder2.transition(HtmlTreeBuilderState.Text);
                        } else if (normalName.equals("xmp")) {
                            if (htmlTreeBuilder2.inButtonScope("p")) {
                                htmlTreeBuilder2.processEndTag("p");
                            }
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.framesetOk(false);
                            HtmlTreeBuilderState.handleRawtext(asStartTag, htmlTreeBuilder2);
                        } else if (normalName.equals("iframe")) {
                            htmlTreeBuilder2.framesetOk(false);
                            HtmlTreeBuilderState.handleRawtext(asStartTag, htmlTreeBuilder2);
                        } else if (normalName.equals("noembed")) {
                            HtmlTreeBuilderState.handleRawtext(asStartTag, htmlTreeBuilder2);
                        } else if (normalName.equals("select")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.insert(asStartTag);
                            htmlTreeBuilder2.framesetOk(false);
                            HtmlTreeBuilderState state = htmlTreeBuilder.state();
                            if (state.equals(HtmlTreeBuilderState.InTable) || state.equals(HtmlTreeBuilderState.InCaption) || state.equals(HtmlTreeBuilderState.InTableBody) || state.equals(HtmlTreeBuilderState.InRow) || state.equals(HtmlTreeBuilderState.InCell)) {
                                htmlTreeBuilder2.transition(HtmlTreeBuilderState.InSelectInTable);
                            } else {
                                htmlTreeBuilder2.transition(HtmlTreeBuilderState.InSelect);
                            }
                        } else if (StringUtil.inSorted(normalName, Constants.InBodyStartOptions)) {
                            if (htmlTreeBuilder.currentElement().nodeName().equals("option")) {
                                htmlTreeBuilder2.processEndTag("option");
                            }
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.insert(asStartTag);
                        } else if (StringUtil.inSorted(normalName, Constants.InBodyStartRuby)) {
                            if (htmlTreeBuilder2.inScope("ruby")) {
                                htmlTreeBuilder.generateImpliedEndTags();
                                if (!htmlTreeBuilder.currentElement().nodeName().equals("ruby")) {
                                    htmlTreeBuilder2.error(this);
                                    htmlTreeBuilder2.popStackToBefore("ruby");
                                }
                                htmlTreeBuilder2.insert(asStartTag);
                            }
                        } else if (normalName.equals("math")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.insert(asStartTag);
                        } else if (normalName.equals("svg")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.insert(asStartTag);
                        } else if (StringUtil.inSorted(normalName, Constants.InBodyStartDrop)) {
                            htmlTreeBuilder2.error(this);
                            return false;
                        } else {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder2.insert(asStartTag);
                        }
                    }
                }
            } else if (i == 4) {
                Token.EndTag asEndTag = token.asEndTag();
                String normalName2 = asEndTag.normalName();
                if (StringUtil.inSorted(normalName2, Constants.InBodyEndAdoptionFormatters)) {
                    int i3 = 0;
                    while (i3 < 8) {
                        Element activeFormattingElement = htmlTreeBuilder2.getActiveFormattingElement(normalName2);
                        if (activeFormattingElement == null) {
                            return anyOtherEndTag(token, htmlTreeBuilder);
                        }
                        if (!htmlTreeBuilder2.onStack(activeFormattingElement)) {
                            htmlTreeBuilder2.error(this);
                            htmlTreeBuilder2.removeFromActiveFormattingElements(activeFormattingElement);
                            return z;
                        } else if (!htmlTreeBuilder2.inScope(activeFormattingElement.nodeName())) {
                            htmlTreeBuilder2.error(this);
                            return false;
                        } else {
                            if (htmlTreeBuilder.currentElement() != activeFormattingElement) {
                                htmlTreeBuilder2.error(this);
                            }
                            ArrayList<Element> stack5 = htmlTreeBuilder.getStack();
                            int size3 = stack5.size();
                            Element element7 = null;
                            int i4 = 0;
                            boolean z2 = false;
                            while (true) {
                                if (i4 >= size3 || i4 >= 64) {
                                    element = null;
                                } else {
                                    element = stack5.get(i4);
                                    if (element != activeFormattingElement) {
                                        if (z2 && htmlTreeBuilder2.isSpecial(element)) {
                                            break;
                                        }
                                    } else {
                                        element7 = stack5.get(i4 - 1);
                                        z2 = true;
                                    }
                                    i4++;
                                }
                            }
                            element = null;
                            if (element == null) {
                                htmlTreeBuilder2.popStackToClose(activeFormattingElement.nodeName());
                                htmlTreeBuilder2.removeFromActiveFormattingElements(activeFormattingElement);
                                return z;
                            }
                            Element element8 = element;
                            Element element9 = element8;
                            for (int i5 = 0; i5 < 3; i5++) {
                                if (htmlTreeBuilder2.onStack(element8)) {
                                    element8 = htmlTreeBuilder2.aboveOnStack(element8);
                                }
                                if (!htmlTreeBuilder2.isInActiveFormattingElements(element8)) {
                                    htmlTreeBuilder2.removeFromStack(element8);
                                } else if (element8 == activeFormattingElement) {
                                    break;
                                } else {
                                    Element element10 = new Element(Tag.valueOf(element8.nodeName(), ParseSettings.preserveCase), htmlTreeBuilder.getBaseUri());
                                    htmlTreeBuilder2.replaceActiveFormattingElement(element8, element10);
                                    htmlTreeBuilder2.replaceOnStack(element8, element10);
                                    if (element9.parent() != null) {
                                        element9.remove();
                                    }
                                    element10.appendChild(element9);
                                    element8 = element10;
                                    element9 = element8;
                                }
                            }
                            if (StringUtil.inSorted(element7.nodeName(), Constants.InBodyEndTableFosters)) {
                                if (element9.parent() != null) {
                                    element9.remove();
                                }
                                htmlTreeBuilder2.insertInFosterParent(element9);
                            } else {
                                if (element9.parent() != null) {
                                    element9.remove();
                                }
                                element7.appendChild(element9);
                            }
                            Element element11 = new Element(activeFormattingElement.tag(), htmlTreeBuilder.getBaseUri());
                            element11.attributes().addAll(activeFormattingElement.attributes());
                            for (Node appendChild : (Node[]) element.childNodes().toArray(new Node[element.childNodeSize()])) {
                                element11.appendChild(appendChild);
                            }
                            element.appendChild(element11);
                            htmlTreeBuilder2.removeFromActiveFormattingElements(activeFormattingElement);
                            htmlTreeBuilder2.removeFromStack(activeFormattingElement);
                            htmlTreeBuilder2.insertOnStackAfter(element, element11);
                            i3++;
                            z = true;
                        }
                    }
                } else if (StringUtil.inSorted(normalName2, Constants.InBodyEndClosers)) {
                    if (!htmlTreeBuilder2.inScope(normalName2)) {
                        htmlTreeBuilder2.error(this);
                        return false;
                    }
                    htmlTreeBuilder.generateImpliedEndTags();
                    if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                        htmlTreeBuilder2.error(this);
                    }
                    htmlTreeBuilder2.popStackToClose(normalName2);
                } else if (normalName2.equals("span")) {
                    return anyOtherEndTag(token, htmlTreeBuilder);
                } else {
                    if (normalName2.equals("li")) {
                        if (!htmlTreeBuilder2.inListItemScope(normalName2)) {
                            htmlTreeBuilder2.error(this);
                            return false;
                        }
                        htmlTreeBuilder2.generateImpliedEndTags(normalName2);
                        if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                            htmlTreeBuilder2.error(this);
                        }
                        htmlTreeBuilder2.popStackToClose(normalName2);
                    } else if (normalName2.equals("body")) {
                        if (!htmlTreeBuilder2.inScope("body")) {
                            htmlTreeBuilder2.error(this);
                            return false;
                        }
                        htmlTreeBuilder2.transition(HtmlTreeBuilderState.AfterBody);
                    } else if (normalName2.equals("html")) {
                        if (htmlTreeBuilder2.processEndTag("body")) {
                            return htmlTreeBuilder2.process(asEndTag);
                        }
                    } else if (normalName2.equals("form")) {
                        FormElement formElement = htmlTreeBuilder.getFormElement();
                        htmlTreeBuilder2.setFormElement((FormElement) null);
                        if (formElement == null || !htmlTreeBuilder2.inScope(normalName2)) {
                            htmlTreeBuilder2.error(this);
                            return false;
                        }
                        htmlTreeBuilder.generateImpliedEndTags();
                        if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                            htmlTreeBuilder2.error(this);
                        }
                        htmlTreeBuilder2.removeFromStack(formElement);
                    } else if (normalName2.equals("p")) {
                        if (!htmlTreeBuilder2.inButtonScope(normalName2)) {
                            htmlTreeBuilder2.error(this);
                            htmlTreeBuilder2.processStartTag(normalName2);
                            return htmlTreeBuilder2.process(asEndTag);
                        }
                        htmlTreeBuilder2.generateImpliedEndTags(normalName2);
                        if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                            htmlTreeBuilder2.error(this);
                        }
                        htmlTreeBuilder2.popStackToClose(normalName2);
                    } else if (!StringUtil.inSorted(normalName2, Constants.DdDt)) {
                        String[] strArr2 = Constants.Headings;
                        if (StringUtil.inSorted(normalName2, strArr2)) {
                            if (!htmlTreeBuilder2.inScope(strArr2)) {
                                htmlTreeBuilder2.error(this);
                                return false;
                            }
                            htmlTreeBuilder2.generateImpliedEndTags(normalName2);
                            if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                                htmlTreeBuilder2.error(this);
                            }
                            htmlTreeBuilder2.popStackToClose(strArr2);
                        } else if (normalName2.equals("sarcasm")) {
                            return anyOtherEndTag(token, htmlTreeBuilder);
                        } else {
                            if (StringUtil.inSorted(normalName2, Constants.InBodyStartApplets)) {
                                if (!htmlTreeBuilder2.inScope("name")) {
                                    if (!htmlTreeBuilder2.inScope(normalName2)) {
                                        htmlTreeBuilder2.error(this);
                                        return false;
                                    }
                                    htmlTreeBuilder.generateImpliedEndTags();
                                    if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                                        htmlTreeBuilder2.error(this);
                                    }
                                    htmlTreeBuilder2.popStackToClose(normalName2);
                                    htmlTreeBuilder.clearFormattingElementsToLastMarker();
                                }
                            } else if (!normalName2.equals("br")) {
                                return anyOtherEndTag(token, htmlTreeBuilder);
                            } else {
                                htmlTreeBuilder2.error(this);
                                htmlTreeBuilder2.processStartTag("br");
                                return false;
                            }
                        }
                    } else if (!htmlTreeBuilder2.inScope(normalName2)) {
                        htmlTreeBuilder2.error(this);
                        return false;
                    } else {
                        htmlTreeBuilder2.generateImpliedEndTags(normalName2);
                        if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName2)) {
                            htmlTreeBuilder2.error(this);
                        }
                        htmlTreeBuilder2.popStackToClose(normalName2);
                    }
                }
            } else if (i == 5) {
                Token.Character asCharacter = token.asCharacter();
                if (asCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                    htmlTreeBuilder2.error(this);
                    return false;
                } else if (!htmlTreeBuilder.framesetOk() || !HtmlTreeBuilderState.isWhitespace((Token) asCharacter)) {
                    htmlTreeBuilder.reconstructFormattingElements();
                    htmlTreeBuilder2.insert(asCharacter);
                    htmlTreeBuilder2.framesetOk(false);
                } else {
                    htmlTreeBuilder.reconstructFormattingElements();
                    htmlTreeBuilder2.insert(asCharacter);
                }
            }
            return true;
        }
    },
    Text {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isCharacter()) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            } else if (token.isEOF()) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return htmlTreeBuilder.process(token);
            } else if (!token.isEndTag()) {
                return true;
            } else {
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return true;
            }
        }
    },
    InTable {
        public boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            if (!StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            htmlTreeBuilder.setFosterInserts(true);
            boolean process = htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            htmlTreeBuilder.setFosterInserts(false);
            return process;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            Token token2 = token;
            HtmlTreeBuilder htmlTreeBuilder2 = htmlTreeBuilder;
            if (token.isCharacter()) {
                htmlTreeBuilder.newPendingTableCharacters();
                htmlTreeBuilder.markInsertionMode();
                htmlTreeBuilder2.transition(HtmlTreeBuilderState.InTableText);
                return htmlTreeBuilder2.process(token2);
            } else if (token.isComment()) {
                htmlTreeBuilder2.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder2.error(this);
                return false;
            } else if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("caption")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insertMarkerToFormattingElements();
                    htmlTreeBuilder2.insert(asStartTag);
                    htmlTreeBuilder2.transition(HtmlTreeBuilderState.InCaption);
                } else if (normalName.equals("colgroup")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder2.insert(asStartTag);
                    htmlTreeBuilder2.transition(HtmlTreeBuilderState.InColumnGroup);
                } else if (normalName.equals("col")) {
                    htmlTreeBuilder2.processStartTag("colgroup");
                    return htmlTreeBuilder2.process(token2);
                } else if (StringUtil.in(normalName, "tbody", "tfoot", "thead")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder2.insert(asStartTag);
                    htmlTreeBuilder2.transition(HtmlTreeBuilderState.InTableBody);
                } else if (StringUtil.in(normalName, "td", "th", "tr")) {
                    htmlTreeBuilder2.processStartTag("tbody");
                    return htmlTreeBuilder2.process(token2);
                } else if (normalName.equals("table")) {
                    htmlTreeBuilder2.error(this);
                    if (htmlTreeBuilder2.processEndTag("table")) {
                        return htmlTreeBuilder2.process(token2);
                    }
                } else if (StringUtil.in(normalName, "style", "script")) {
                    return htmlTreeBuilder2.process(token2, HtmlTreeBuilderState.InHead);
                } else {
                    if (normalName.equals("input")) {
                        if (!asStartTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                            return anythingElse(token, htmlTreeBuilder);
                        }
                        htmlTreeBuilder2.insertEmpty(asStartTag);
                    } else if (!normalName.equals("form")) {
                        return anythingElse(token, htmlTreeBuilder);
                    } else {
                        htmlTreeBuilder2.error(this);
                        if (htmlTreeBuilder.getFormElement() != null) {
                            return false;
                        }
                        htmlTreeBuilder2.insertForm(asStartTag, false);
                    }
                }
                return true;
            } else if (token.isEndTag()) {
                String normalName2 = token.asEndTag().normalName();
                if (normalName2.equals("table")) {
                    if (!htmlTreeBuilder2.inTableScope(normalName2)) {
                        htmlTreeBuilder2.error(this);
                        return false;
                    }
                    htmlTreeBuilder2.popStackToClose("table");
                    htmlTreeBuilder.resetInsertionMode();
                    return true;
                } else if (!StringUtil.in(normalName2, "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    htmlTreeBuilder2.error(this);
                    return false;
                }
            } else if (!token.isEOF()) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder2.error(this);
                }
                return true;
            }
        }
    },
    InTableText {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()] != 5) {
                if (htmlTreeBuilder.getPendingTableCharacters().size() > 0) {
                    for (String next : htmlTreeBuilder.getPendingTableCharacters()) {
                        if (!HtmlTreeBuilderState.isWhitespace(next)) {
                            htmlTreeBuilder.error(this);
                            if (StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                htmlTreeBuilder.setFosterInserts(true);
                                htmlTreeBuilder.process(new Token.Character().data(next), HtmlTreeBuilderState.InBody);
                                htmlTreeBuilder.setFosterInserts(false);
                            } else {
                                htmlTreeBuilder.process(new Token.Character().data(next), HtmlTreeBuilderState.InBody);
                            }
                        } else {
                            htmlTreeBuilder.insert(new Token.Character().data(next));
                        }
                    }
                    htmlTreeBuilder.newPendingTableCharacters();
                }
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return htmlTreeBuilder.process(token);
            }
            Token.Character asCharacter = token.asCharacter();
            if (asCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                htmlTreeBuilder.error(this);
                return false;
            }
            htmlTreeBuilder.getPendingTableCharacters().add(asCharacter.getData());
            return true;
        }
    },
    InCaption {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (!token.isEndTag() || !token.asEndTag().normalName().equals("caption")) {
                if ((token.isStartTag() && StringUtil.in(token.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) || (token.isEndTag() && token.asEndTag().normalName().equals("table"))) {
                    htmlTreeBuilder.error(this);
                    if (htmlTreeBuilder.processEndTag("caption")) {
                        return htmlTreeBuilder.process(token);
                    }
                    return true;
                } else if (!token.isEndTag() || !StringUtil.in(token.asEndTag().normalName(), "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
                } else {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            } else if (!htmlTreeBuilder.inTableScope(token.asEndTag().normalName())) {
                htmlTreeBuilder.error(this);
                return false;
            } else {
                htmlTreeBuilder.generateImpliedEndTags();
                if (!htmlTreeBuilder.currentElement().nodeName().equals("caption")) {
                    htmlTreeBuilder.error(this);
                }
                htmlTreeBuilder.popStackToClose("caption");
                htmlTreeBuilder.clearFormattingElementsToLastMarker();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
                return true;
            }
        }
    },
    InColumnGroup {
        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.processEndTag("colgroup")) {
                return treeBuilder.process(token);
            }
            return true;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            int i = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i == 1) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (i == 2) {
                htmlTreeBuilder.error(this);
            } else if (i == 3) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                normalName.getClass();
                if (normalName.equals("col")) {
                    htmlTreeBuilder.insertEmpty(asStartTag);
                } else if (!normalName.equals("html")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
                }
            } else if (i != 4) {
                if (i != 6) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    return true;
                }
                return anythingElse(token, htmlTreeBuilder);
            } else if (!token.asEndTag().normalName.equals("colgroup")) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
            }
            return true;
        }
    },
    InTableBody {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InTable);
        }

        private boolean exitTableBody(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (htmlTreeBuilder.inTableScope("tbody") || htmlTreeBuilder.inTableScope("thead") || htmlTreeBuilder.inScope("tfoot")) {
                htmlTreeBuilder.clearStackToTableBodyContext();
                htmlTreeBuilder.processEndTag(htmlTreeBuilder.currentElement().nodeName());
                return htmlTreeBuilder.process(token);
            }
            htmlTreeBuilder.error(this);
            return false;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            int i = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i == 3) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("template")) {
                    htmlTreeBuilder.insert(asStartTag);
                    return true;
                } else if (normalName.equals("tr")) {
                    htmlTreeBuilder.clearStackToTableBodyContext();
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InRow);
                    return true;
                } else if (StringUtil.in(normalName, "th", "td")) {
                    htmlTreeBuilder.error(this);
                    htmlTreeBuilder.processStartTag("tr");
                    return htmlTreeBuilder.process(asStartTag);
                } else if (StringUtil.in(normalName, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) {
                    return exitTableBody(token, htmlTreeBuilder);
                } else {
                    return anythingElse(token, htmlTreeBuilder);
                }
            } else if (i != 4) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                String normalName2 = token.asEndTag().normalName();
                if (StringUtil.in(normalName2, "tbody", "tfoot", "thead")) {
                    if (!htmlTreeBuilder.inTableScope(normalName2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.clearStackToTableBodyContext();
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
                    return true;
                } else if (normalName2.equals("table")) {
                    return exitTableBody(token, htmlTreeBuilder);
                } else {
                    if (!StringUtil.in(normalName2, "body", "caption", "col", "colgroup", "html", "td", "th", "tr")) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
        }
    },
    InRow {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InTable);
        }

        private boolean handleMissingTr(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.processEndTag("tr")) {
                return treeBuilder.process(token);
            }
            return false;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                if (normalName.equals("template")) {
                    htmlTreeBuilder.insert(asStartTag);
                    return true;
                } else if (StringUtil.in(normalName, "th", "td")) {
                    htmlTreeBuilder.clearStackToTableRowContext();
                    htmlTreeBuilder.insert(asStartTag);
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InCell);
                    htmlTreeBuilder.insertMarkerToFormattingElements();
                    return true;
                } else if (StringUtil.in(normalName, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                    return handleMissingTr(token, htmlTreeBuilder);
                } else {
                    return anythingElse(token, htmlTreeBuilder);
                }
            } else if (!token.isEndTag()) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                String normalName2 = token.asEndTag().normalName();
                if (normalName2.equals("tr")) {
                    if (!htmlTreeBuilder.inTableScope(normalName2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.clearStackToTableRowContext();
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InTableBody);
                    return true;
                } else if (normalName2.equals("table")) {
                    return handleMissingTr(token, htmlTreeBuilder);
                } else {
                    if (StringUtil.in(normalName2, "tbody", "tfoot", "thead")) {
                        if (!htmlTreeBuilder.inTableScope(normalName2)) {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.processEndTag("tr");
                        return htmlTreeBuilder.process(token);
                    } else if (!StringUtil.in(normalName2, "body", "caption", "col", "colgroup", "html", "td", "th")) {
                        return anythingElse(token, htmlTreeBuilder);
                    } else {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                }
            }
        }
    },
    InCell {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
        }

        private void closeCell(HtmlTreeBuilder htmlTreeBuilder) {
            if (htmlTreeBuilder.inTableScope("td")) {
                htmlTreeBuilder.processEndTag("td");
            } else {
                htmlTreeBuilder.processEndTag("th");
            }
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isEndTag()) {
                String normalName = token.asEndTag().normalName();
                if (StringUtil.in(normalName, "td", "th")) {
                    if (!htmlTreeBuilder.inTableScope(normalName)) {
                        htmlTreeBuilder.error(this);
                        htmlTreeBuilder.transition(HtmlTreeBuilderState.InRow);
                        return false;
                    }
                    htmlTreeBuilder.generateImpliedEndTags();
                    if (!htmlTreeBuilder.currentElement().nodeName().equals(normalName)) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose(normalName);
                    htmlTreeBuilder.clearFormattingElementsToLastMarker();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InRow);
                    return true;
                } else if (StringUtil.in(normalName, "body", "caption", "col", "colgroup", "html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else if (!StringUtil.in(normalName, "table", "tbody", "tfoot", "thead", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                } else {
                    if (!htmlTreeBuilder.inTableScope(normalName)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
            } else if (!token.isStartTag() || !StringUtil.in(token.asStartTag().normalName(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                return anythingElse(token, htmlTreeBuilder);
            } else {
                if (htmlTreeBuilder.inTableScope("td") || htmlTreeBuilder.inTableScope("th")) {
                    closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
        }
    },
    InSelect {
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            return false;
        }

        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
                case 1:
                    htmlTreeBuilder.insert(token.asComment());
                    break;
                case 2:
                    htmlTreeBuilder.error(this);
                    return false;
                case 3:
                    Token.StartTag asStartTag = token.asStartTag();
                    String normalName = asStartTag.normalName();
                    if (normalName.equals("html")) {
                        return htmlTreeBuilder.process(asStartTag, HtmlTreeBuilderState.InBody);
                    }
                    if (normalName.equals("option")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals("option")) {
                            htmlTreeBuilder.processEndTag("option");
                        }
                        htmlTreeBuilder.insert(asStartTag);
                        break;
                    } else if (normalName.equals("optgroup")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals("option")) {
                            htmlTreeBuilder.processEndTag("option");
                        } else if (htmlTreeBuilder.currentElement().nodeName().equals("optgroup")) {
                            htmlTreeBuilder.processEndTag("optgroup");
                        }
                        htmlTreeBuilder.insert(asStartTag);
                        break;
                    } else if (normalName.equals("select")) {
                        htmlTreeBuilder.error(this);
                        return htmlTreeBuilder.processEndTag("select");
                    } else if (StringUtil.in(normalName, "input", "keygen", "textarea")) {
                        htmlTreeBuilder.error(this);
                        if (!htmlTreeBuilder.inSelectScope("select")) {
                            return false;
                        }
                        htmlTreeBuilder.processEndTag("select");
                        return htmlTreeBuilder.process(asStartTag);
                    } else if (normalName.equals("script")) {
                        return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
                    } else {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                case 4:
                    String normalName2 = token.asEndTag().normalName();
                    normalName2.getClass();
                    char c = 65535;
                    switch (normalName2.hashCode()) {
                        case -1010136971:
                            if (normalName2.equals("option")) {
                                c = 0;
                                break;
                            }
                            break;
                        case -906021636:
                            if (normalName2.equals("select")) {
                                c = 1;
                                break;
                            }
                            break;
                        case -80773204:
                            if (normalName2.equals("optgroup")) {
                                c = 2;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            if (!htmlTreeBuilder.currentElement().nodeName().equals("option")) {
                                htmlTreeBuilder.error(this);
                                break;
                            } else {
                                htmlTreeBuilder.pop();
                                break;
                            }
                        case 1:
                            if (htmlTreeBuilder.inSelectScope(normalName2)) {
                                htmlTreeBuilder.popStackToClose(normalName2);
                                htmlTreeBuilder.resetInsertionMode();
                                break;
                            } else {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                        case 2:
                            if (htmlTreeBuilder.currentElement().nodeName().equals("option") && htmlTreeBuilder.aboveOnStack(htmlTreeBuilder.currentElement()) != null && htmlTreeBuilder.aboveOnStack(htmlTreeBuilder.currentElement()).nodeName().equals("optgroup")) {
                                htmlTreeBuilder.processEndTag("option");
                            }
                            if (!htmlTreeBuilder.currentElement().nodeName().equals("optgroup")) {
                                htmlTreeBuilder.error(this);
                                break;
                            } else {
                                htmlTreeBuilder.pop();
                                break;
                            }
                        default:
                            return anythingElse(token, htmlTreeBuilder);
                    }
                case 5:
                    Token.Character asCharacter = token.asCharacter();
                    if (!asCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                        htmlTreeBuilder.insert(asCharacter);
                        break;
                    } else {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                case 6:
                    if (!htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                        htmlTreeBuilder.error(this);
                        break;
                    }
                    break;
                default:
                    return anythingElse(token, htmlTreeBuilder);
            }
            return true;
        }
    },
    InSelectInTable {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag() && StringUtil.in(token.asStartTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.processEndTag("select");
                return htmlTreeBuilder.process(token);
            } else if (!token.isEndTag() || !StringUtil.in(token.asEndTag().normalName(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InSelect);
            } else {
                htmlTreeBuilder.error(this);
                if (!htmlTreeBuilder.inTableScope(token.asEndTag().normalName())) {
                    return false;
                }
                htmlTreeBuilder.processEndTag("select");
                return htmlTreeBuilder.process(token);
            }
        }
    },
    AfterBody {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            } else {
                if (!token.isEndTag() || !token.asEndTag().normalName().equals("html")) {
                    if (token.isEOF()) {
                        return true;
                    }
                    htmlTreeBuilder.error(this);
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InBody);
                    return htmlTreeBuilder.process(token);
                } else if (htmlTreeBuilder.isFragmentParsing()) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterAfterBody);
                    return true;
                }
            }
        }
    },
    InFrameset {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
            } else if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag()) {
                Token.StartTag asStartTag = token.asStartTag();
                String normalName = asStartTag.normalName();
                normalName.getClass();
                char c = 65535;
                switch (normalName.hashCode()) {
                    case -1644953643:
                        if (normalName.equals("frameset")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 3213227:
                        if (normalName.equals("html")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 97692013:
                        if (normalName.equals("frame")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1192721831:
                        if (normalName.equals("noframes")) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        htmlTreeBuilder.insert(asStartTag);
                        break;
                    case 1:
                        return htmlTreeBuilder.process(asStartTag, HtmlTreeBuilderState.InBody);
                    case 2:
                        htmlTreeBuilder.insertEmpty(asStartTag);
                        break;
                    case 3:
                        return htmlTreeBuilder.process(asStartTag, HtmlTreeBuilderState.InHead);
                    default:
                        htmlTreeBuilder.error(this);
                        return false;
                }
            } else if (!token.isEndTag() || !token.asEndTag().normalName().equals("frameset")) {
                if (!token.isEOF()) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else if (!htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder.error(this);
                }
            } else if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                htmlTreeBuilder.error(this);
                return false;
            } else {
                htmlTreeBuilder.pop();
                if (!htmlTreeBuilder.isFragmentParsing() && !htmlTreeBuilder.currentElement().nodeName().equals("frameset")) {
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterFrameset);
                }
            }
            return true;
        }
    },
    AfterFrameset {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            } else if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (token.isStartTag() && token.asStartTag().normalName().equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            } else {
                if (token.isEndTag() && token.asEndTag().normalName().equals("html")) {
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterAfterFrameset);
                    return true;
                } else if (token.isStartTag() && token.asStartTag().normalName().equals("noframes")) {
                    return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
                } else {
                    if (token.isEOF()) {
                        return true;
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
        }
    },
    AfterAfterBody {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || (token.isStartTag() && token.asStartTag().normalName().equals("html"))) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            } else {
                if (token.isEOF()) {
                    return true;
                }
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InBody);
                return htmlTreeBuilder.process(token);
            }
        }
    },
    AfterAfterFrameset {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || (token.isStartTag() && token.asStartTag().normalName().equals("html"))) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            } else {
                if (token.isEOF()) {
                    return true;
                }
                if (token.isStartTag() && token.asStartTag().normalName().equals("noframes")) {
                    return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
        }
    },
    ForeignContent {
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return true;
        }
    };
    
    /* access modifiers changed from: private */
    public static String nullString;

    /* renamed from: org.jsoup.parser.HtmlTreeBuilderState$24  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass24 {
        static final /* synthetic */ int[] $SwitchMap$org$jsoup$parser$Token$TokenType = null;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                org.jsoup.parser.Token$TokenType[] r0 = org.jsoup.parser.Token.TokenType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$jsoup$parser$Token$TokenType = r0
                org.jsoup.parser.Token$TokenType r1 = org.jsoup.parser.Token.TokenType.Comment     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$org$jsoup$parser$Token$TokenType     // Catch:{ NoSuchFieldError -> 0x001d }
                org.jsoup.parser.Token$TokenType r1 = org.jsoup.parser.Token.TokenType.Doctype     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$org$jsoup$parser$Token$TokenType     // Catch:{ NoSuchFieldError -> 0x0028 }
                org.jsoup.parser.Token$TokenType r1 = org.jsoup.parser.Token.TokenType.StartTag     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$org$jsoup$parser$Token$TokenType     // Catch:{ NoSuchFieldError -> 0x0033 }
                org.jsoup.parser.Token$TokenType r1 = org.jsoup.parser.Token.TokenType.EndTag     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$org$jsoup$parser$Token$TokenType     // Catch:{ NoSuchFieldError -> 0x003e }
                org.jsoup.parser.Token$TokenType r1 = org.jsoup.parser.Token.TokenType.Character     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$org$jsoup$parser$Token$TokenType     // Catch:{ NoSuchFieldError -> 0x0049 }
                org.jsoup.parser.Token$TokenType r1 = org.jsoup.parser.Token.TokenType.EOF     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass24.<clinit>():void");
        }
    }

    public static final class Constants {
        static final String[] DdDt = null;
        static final String[] Formatters = null;
        static final String[] Headings = null;
        static final String[] InBodyEndAdoptionFormatters = null;
        static final String[] InBodyEndClosers = null;
        static final String[] InBodyEndTableFosters = null;
        static final String[] InBodyStartApplets = null;
        static final String[] InBodyStartDrop = null;
        static final String[] InBodyStartEmptyFormatters = null;
        static final String[] InBodyStartInputAttribs = null;
        static final String[] InBodyStartLiBreakers = null;
        static final String[] InBodyStartMedia = null;
        static final String[] InBodyStartOptions = null;
        static final String[] InBodyStartPClosers = null;
        static final String[] InBodyStartPreListing = null;
        static final String[] InBodyStartRuby = null;
        static final String[] InBodyStartToHead = null;

        static {
            InBodyStartToHead = new String[]{"base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "title"};
            InBodyStartPClosers = new String[]{"address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
            Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
            InBodyStartPreListing = new String[]{"listing", "pre"};
            InBodyStartLiBreakers = new String[]{"address", "div", "p"};
            DdDt = new String[]{"dd", "dt"};
            Formatters = new String[]{"b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u"};
            InBodyStartApplets = new String[]{"applet", "marquee", "object"};
            InBodyStartEmptyFormatters = new String[]{"area", "br", "embed", "img", "keygen", "wbr"};
            InBodyStartMedia = new String[]{"param", "source", "track"};
            InBodyStartInputAttribs = new String[]{"action", "name", "prompt"};
            InBodyStartOptions = new String[]{"optgroup", "option"};
            InBodyStartRuby = new String[]{"rp", "rt"};
            InBodyStartDrop = new String[]{"caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
            InBodyEndClosers = new String[]{"address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
            InBodyEndAdoptionFormatters = new String[]{"a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"};
            InBodyEndTableFosters = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
        }
    }

    /* access modifiers changed from: public */
    static {
        nullString = String.valueOf(0);
    }

    /* access modifiers changed from: private */
    public static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rawtext);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
        htmlTreeBuilder.insert(startTag);
    }

    /* access modifiers changed from: private */
    public static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rcdata);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
        htmlTreeBuilder.insert(startTag);
    }

    /* access modifiers changed from: private */
    public static boolean isWhitespace(Token token) {
        if (token.isCharacter()) {
            return isWhitespace(token.asCharacter().getData());
        }
        return false;
    }

    public abstract boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder);

    /* access modifiers changed from: private */
    public static boolean isWhitespace(String str) {
        return StringUtil.isBlank(str);
    }
}
