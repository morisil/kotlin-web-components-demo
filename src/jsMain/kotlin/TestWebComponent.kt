/*
 * Copyright 2025 Kazimierz Pogoda / Xemantic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xemantic.kotlin.webcomponents.demo

import js.core.JsAny
import web.components.CustomElement
import web.components.ShadowRoot
import web.components.ShadowRootInit
import web.components.ShadowRootMode
import web.components.open
import web.cssom.CSSStyleSheet
import web.dom.Element
import web.dom.document
import web.html.HTMLElement

private val style = style("""
h1 {
    background: black;
}
""".trimIndent())

@OptIn(ExperimentalJsExport::class)
@JsExport
@JsName("TestWebComponent")
class TestWebComponent : XemanticWebComponent(style) {

    override fun rootElement(): Element {
        return document.createElement("h1").apply {
            innerText = "Hello from WebComponent"
        }
    }

}

fun style(/* language=css */ string: String): CSSStyleSheet = CSSStyleSheet().apply {
    replaceSync(string)
}

abstract class XemanticWebComponent(
    private val css: CSSStyleSheet
) : HTMLElement(), CustomElement.WithCallbacks {

    val shadow: ShadowRoot = this.attachShadow(ShadowRootInit(mode = ShadowRootMode.open))
    init {
        shadow.adoptedStyleSheets = arrayOf(css)
    }

    abstract fun rootElement(): Element

    override fun connectedCallback() {
        shadow.appendChild(rootElement())
    }

    override fun disconnectedCallback() {
    }

    override fun adoptedCallback() {
    }

    override fun attributeChangedCallback(name: String, oldValue: JsAny?, newValue: JsAny?) {
    }

}