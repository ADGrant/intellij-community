/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.reactiveidea

/*
* Copyright 2000-2015 JetBrains s.r.o.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.openapi.util.AsyncResult
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import com.intellij.util.WaitFor
import com.jetbrains.reactiveidea.serverModel
import com.jetbrains.reactivemodel.Path
import com.jetbrains.reactivemodel.ReactiveModel
import com.jetbrains.reactivemodel.models.PrimitiveModel
import com.jetbrains.reactivemodel.putIn
import com.jetbrains.reactivemodel.signals.reaction
import com.jetbrains.reactivemodel.util.Lifetime
import java.util.concurrent.Future
import kotlin.test.assertEquals
import kotlin.test.assertTrue

public class DocumentSyncTest : LightPlatformCodeInsightFixtureTestCase() {
  public fun testSimpleDocumentSync() {
    var modelRef: ReactiveModel? = null
    val mirror = ReactiveModel(Lifetime.Eternal) { diff ->
      modelRef!!.performTransaction { m ->
        m.patch(diff)
      }
    }

    val model = ReactiveModel(Lifetime.Eternal) { diff ->
      mirror.performTransaction { m ->
        m.patch(diff)
      }
    }
    modelRef = model

    val first = DocumentImpl("my test document")
    val firstDocumentHost = DocumentHost(Lifetime.Eternal, model, Path("document"), first, getProject(), true)

    val second = DocumentImpl("")
    val secondDocumentHost = DocumentHost(Lifetime.Eternal, mirror, Path("document"), second, getProject(), false)

    first.insertString(0, "hello world!\n")
    first.insertString(0, "abcd\n")
    first.insertString(0, "fuck!\n")

    second.insertString(0, "blabla")

    assertEquals(first.getText(), second.getText())
  }

  public fun testNetworking() {
    val port = 12345

    val server = serverModel(Lifetime.Eternal, port, { model ->
      model.transaction { m ->
        Path("a").putIn(m, PrimitiveModel("abcd"))
      }
    })

    val clientModel = clientModel("http://localhost:" + port, Lifetime.Eternal)
    var clientModelChanged = false
    reaction(false, "waiting for change to come ", clientModel.subscribe(Lifetime.Eternal, Path("a"))) { a ->
      clientModelChanged = true
    }

    object: WaitFor(5000) {
      override fun condition(): Boolean {
        return clientModelChanged
      }
    }
    server.stop()
    assertTrue(clientModelChanged)
  }

  public fun testDocumentsSyncByNetwork() {
    val port = 12345

    val first = DocumentImpl("my test document")
    val second = DocumentImpl("")
    val modelFuture = AsyncResult<ReactiveModel>()
    val server = serverModel(Lifetime.Eternal, port, { model ->
      modelFuture.setDone(model)
    })

    val clientModel = clientModel("http://localhost:" + port, Lifetime.Eternal)

    val serverModel = modelFuture.getResultSync()

    val firstHost = DocumentHost(Lifetime.Eternal, serverModel, Path("document"), first,getProject(), true)
    val secondHost = DocumentHost(Lifetime.Eternal, clientModel, Path("document"), second,getProject(), false)


    ApplicationManager.getApplication().runWriteAction {
      first.insertString(0, "hello world\n")
    }


    var clientModelChanged = false
    reaction(true, "waiting for change to come ", clientModel.subscribe(Lifetime.Eternal, Path("document") / "events")) { a ->
      clientModelChanged = true
    }

    object : WaitFor(5000) {
      override fun condition(): Boolean {
        return clientModelChanged
      }
    }
    server.stop()
    assertTrue(clientModelChanged)
    assertEquals(first.getText(), second.getText())
  }
}