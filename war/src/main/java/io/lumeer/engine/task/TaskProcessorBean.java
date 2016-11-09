/*
 * -----------------------------------------------------------------------\
 * Lumeer
 *  
 * Copyright (C) 2016 the original author or authors.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -----------------------------------------------------------------------/
 */
package io.lumeer.engine.task;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author <a href="mailto:marvenec@gmail.com">Martin Večeřa</a>
 */
@MessageDriven(mappedName = "TaskProcessor", activationConfig = {
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/TaskQueue") })
public class TaskProcessorBean implements MessageListener {

   @Resource
   private MessageDrivenContext mdc;

   @Override
   public void onMessage(final Message message) {
      if (message instanceof TextMessage) {
         System.out.println("Processing incoming message: " + message.toString());
      }
   }
}