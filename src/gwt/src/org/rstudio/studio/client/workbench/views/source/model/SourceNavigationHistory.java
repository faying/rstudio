/*
 * SourceNavigationHistory.java
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.workbench.views.source.model;

import java.util.LinkedList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;



public class SourceNavigationHistory
{
   public SourceNavigationHistory(int maxItems)
   {
      maxItems_ = maxItems;
      history_ = new LinkedList<SourceNavigation>();
      currentLocation_ = -1;
   }
  
   public void add(SourceNavigation navigation)
   {
      // rewind the history to the current location
      while ( (history_.size() - 1) > currentLocation_)
         history_.removeLast();
      
      // screen out duplicates
      if (history_.size() == 0 || !history_.getLast().isEqualTo(navigation))
      {         
         // implement capacity restriction
         if (history_.size() == maxItems_)
            history_.removeFirst();
         
         // add the item and set the current location
         history_.add(navigation);
         currentLocation_ = history_.size() - 1;
      }
      
      fireChangeEvent();
   }
   
   
   public void clear()
   {
      history_.clear();
      currentLocation_ = -1;
      fireChangeEvent();
   }
   
   public boolean isBackEnabled()
   {
      return currentLocation_ >= 0;
   }
   
   public boolean isForwardEnabled()
   {
      return currentLocation_ < (history_.size() - 1);
   }
   
   public SourceNavigation goBack()
   {
      if (!isBackEnabled())
         return null;
      
      SourceNavigation navigation = history_.get(currentLocation_--);
      fireChangeEvent();
      return navigation;
        
   }
  
   public SourceNavigation goForward()
   {
      if (!isForwardEnabled())
         return null;
      
      SourceNavigation navigation = history_.get(++currentLocation_);
      fireChangeEvent();
      return navigation;
   }
   
   public HandlerRegistration addChangeHandler(ChangeHandler handler)
   {
      return handlerManager_.addHandler(ChangeEvent.getType(), handler);
   }
   
   private void fireChangeEvent()
   {
      DomEvent.fireNativeEvent(Document.get().createChangeEvent(), 
                               handlerManager_);
     
   }
 
  
   private final int maxItems_;
   private LinkedList<SourceNavigation> history_;
   private int currentLocation_;
   private HandlerManager handlerManager_ = new HandlerManager(this);
}