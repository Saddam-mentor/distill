<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

	<f:view>
	
		<h:form style="background-color:whitesmoke;">
		<div style="width: 100%; height: 35px;">
				<div style="padding-top: 2px; padding-bottom: 2px; margin-top: 2px;"
					align="center">
					<h:outputText value="Challan Deposit Report"
						styleClass="generalExciseStyle1"
						style="font-weight: bold; text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:35px;"></h:outputText>
				</div>

			</div>
			<TABLE width="80%">
									<TBODY>
										<TR>
											<TD align="left"><h3>
													<h:messages errorStyle="color:red" layout="table"
														id="messages" infoStyle="color:green">
													</h:messages>
												</h3></TD>
										</TR>
									</TBODY>
								</TABLE>
			<rich:spacer height="30"></rich:spacer>
			
		<div class="form-group">
		
		
		<div class="row">
		
		<div class="col-md-4"></div>
		<div class="col-md-4" align="center">
		
		<h:selectOneRadio value="#{challanReportAction.radio}" onchange="this.form.submit();" valueChangeListener="#{challanReportAction.datatableData}">
		
		<f:selectItem itemLabel="PendingVerification" itemValue="P" />
		<f:selectItem itemLabel="Verified" itemValue="V"/>
		
		</h:selectOneRadio>
		
		</div>
		<div class="col-md-4"></div>
		</div>
		
		
		
		
		<div class="row" align="center">
		
		<rich:dataTable width="80%" var ="list" value="#{challanReportAction.list }">
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Sr.No" />
		</f:facet>
		<h:outputText value="#{list.srno}" />
		</rich:column>
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Challan No" />
		</f:facet>
		<h:outputText value="#{list.challanNo}" />
		</rich:column>
		
		
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Challan Date" />
		</f:facet>
		<h:outputText value="#{list.challandate}">
		<f:convertDateTime pattern="dd/MM/yyyy"	timeZone="GMT+5:30" />
		</h:outputText>
		</rich:column>
		
		
		
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Deposit By" />
		</f:facet>
		<h:outputText value="#{list.entry_by}" />
		</rich:column>
		
		
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Head Code" />
		</f:facet>
		<h:outputText value="#{list.headcode}" />
		</rich:column>
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="G6Code" />
		</f:facet>
		<h:outputText value="#{list.g6code}" />
		</rich:column>
		
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Amount" />
		</f:facet>
		<h:outputText value="#{list.amount}" />
		</rich:column>
		
		
		
		<rich:column>
		<f:facet name="header">
		<h:outputText value="Purpose" />
		</f:facet>
		<h:outputText value="#{list.purpose}" />
		</rich:column>
		
		
		
		
		<rich:column rendered="#{challanReportAction.radio eq 'P' }">
		<f:facet name="header">
		<h:outputText value="Verify" />
		</f:facet>
		<h:commandButton value="Approve" action="#{challanReportAction.approveData }" styleClass="btn btn-primary">
		<f:setPropertyActionListener target="#{challanReportAction.dt}" value="#{list}" />
		</h:commandButton>
		</rich:column>
		
		
		
		</rich:dataTable>
		
		</div>
		
		
		
		</div>
		
		
			
		</h:form></f:view>
</ui:composition>