/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.apache.linkis.computation.client.operator.impl

import org.apache.linkis.computation.client.once.action.EngineOperateAction
import org.apache.linkis.computation.client.operator.OnceJobOperator


class ApplicationInfoOperator extends OnceJobOperator[ApplicationInfo] {

  override def getName: String = ApplicationInfoOperator.OPERATOR_NAME

  override def apply(): ApplicationInfo = {

    val engineOperateAction = EngineOperateAction.newBuilder()
      .operatorName(getName)
      .setUser(getUser)
      .setApplicationName(getServiceInstance.getApplicationName)
      .setInstance(getServiceInstance.getInstance)
      .build()

    val result = getLinkisManagerClient.executeEngineOperation(engineOperateAction)
    ApplicationInfo(
      result.getAs("applicationId").getOrElse(""),
      result.getAs("applicationUrl").getOrElse(""),
      result.getAs("queue").getOrElse("")
    )
  }

}

object ApplicationInfoOperator {
  val OPERATOR_NAME = "application"
}

case class ApplicationInfo(applicationId: String, applicationUrl: String, queue: String)
