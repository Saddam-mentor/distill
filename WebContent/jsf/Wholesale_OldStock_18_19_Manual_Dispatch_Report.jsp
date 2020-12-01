 <ui:composition
       xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
	  xmlns:rich="http://richfaces.org/rich">

   <f:view>
     <h:form>
     
     <div class="row">
      <rich:spacer height="20"></rich:spacer>
      <h:messages errorStyle="color:red" layout="table"
		id="messages" infoStyle="color:green">
		</h:messages></div>
		<div class="row">
      <rich:spacer height="20"></rich:spacer>
      <div align="center">
         <h:outputText value="Wholesale Old Stock (2018-19) Manual Dispatch Report"
										style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"></h:outputText>
       </div>
       <rich:spacer height="20"></rich:spacer>
       <rich:separator lineType="dashed"></rich:separator>
     </div>
     <rich:spacer height="30"></rich:spacer>
     
     <div align="center">
        <h:selectOneRadio value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.liqourType }">
        <f:selectItem itemLabel="FL2" itemValue="FL2"/>
        <f:selectItem itemLabel="FL2B" itemValue="FL2B"/>
        <f:selectItem itemLabel="FL2D" itemValue="FL2D"/>
        </h:selectOneRadio>
     </div>
     <rich:spacer height="20"></rich:spacer>
     
     <div align="center">
        <h:selectOneRadio value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.distType }"
        valueChangeListener="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.listener}"
        onchange="this.form.submit();">
        <f:selectItem itemLabel="All" itemValue="A"/>
        <f:selectItem itemLabel="District And Shop Wise" itemValue="D"/>
        
        </h:selectOneRadio>
     </div>
     <h:panelGroup rendered="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.flag1}">
     <rich:spacer height="30"></rich:spacer>
     <div align="center"> 
     <h:outputText value="District : " />  
       <h:selectOneMenu value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.district_id}"
       valueChangeListener="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.getDistName}"
       onchange="this.form.submit();" style="width: 300px;">
       <f:selectItems value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.disttrictList}"/>
       </h:selectOneMenu>
     </div>
     <rich:spacer height="30"></rich:spacer>
     <div align="center"> 
     <h:outputText value="Shop Name : " /> 
       <h:selectOneMenu value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.shop_id }"
       rendered="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.flag1}" style="width: 300px;">
       <f:selectItems value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.shopList}"/>
       </h:selectOneMenu>
     </div>
     </h:panelGroup>
      <rich:spacer height="30"></rich:spacer>
      <div align="center">
         <h:outputLabel value="From Date : "></h:outputLabel>
         <rich:calendar value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.from_dt}"></rich:calendar>
         <h:outputLabel value=" To Date : "></h:outputLabel>
         <rich:calendar value="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.to_dt}"></rich:calendar>
      </div>
      <rich:spacer height="20"></rich:spacer>
      <div align="center">
      <h:commandButton value="Print" action="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.print}" 
      styleClass="btn btn-primary"></h:commandButton>
      <h:outputLink value="/doc/ExciseUp/MIS/pdf//#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.pdfName}"
      target="_blank">
      <h:outputText value="View Report" rendered="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.flag}"/>
      </h:outputLink>
      </div>
      <rich:spacer height="10"></rich:spacer>
      
     
      <div align="center">
      <h:commandButton value="Generate Excel" action="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.excel}" 
      styleClass="btn btn-primary"></h:commandButton>
      <h:outputLink value="/doc/ExciseUp/MIS/Excel//#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.exlname}"
        target="_blank">
      <h:outputText value="download Excel" rendered="#{wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action.excelFlag}"/>
      </h:outputLink>
      </div>
      <rich:spacer height="5"></rich:spacer>
     </h:form>
   
   </f:view>
</ui:composition>