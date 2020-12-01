<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
<style>

.headercell {
    border-right: solid 1px #C4C0C9;
    border-bottom: solid 1px #C4C0C9;
    padding: 20px 4px 4px 4px;
    color: #071aed;
    text-align: center;
    font-weight: bold;
    font-size: 20px;
    font-family: Arial,Verdana,sans-serif;
}
.rich-table-subheadercell {
    text-align: center;
    font-size: 15px;
    color: #000;
    font-family: Arial,Verdana,sans-serif;
    white-space: nowrap;
}
.rich-table-headercell {
    border-right: solid 1px #C4C0C9;
    border-bottom: solid 1px #C4C0C9;
    padding: 20px 4px 4px 4px;
    color: #000;
    text-align: center;
    font-weight: bold;
    font-size: 20px;
    font-family: Arial,Verdana,sans-serif;
}
</style>

		<h:form  >
		<div class="panel panel-default">
			<div class="panel-body">
			<TABLE width="100%"    align="center">
 			
				 
					<TR>
						<TD>
							<TABLE width="80%">
								<TBODY >
									<TR>
										<TD align="left" ><h3><h:messages errorStyle="color:red"
												  layout="table" id="messages"
												infoStyle="color:green">
											</h:messages></h3></TD>
									</TR>
								</TBODY>
							</TABLE>
							
							<div align="center">
							<h1 style=" font-style: italic;text-decoration: underline;color:#071aed; background-color:#F4E1D4">Daily Statistic Consolidated Report For:: 
							<h:outputText value="#{dailyStatisticReportAction.date }">
							<f:convertDateTime pattern="dd/MM/yyyy" timeZone="GMT"/>
							 </h:outputText></h1>
							</div>
							
							<rich:dataTable var="list" value="#{dailyStatisticReportAction.dailyStaistic}" width="100%">
							<f:facet name="header">
							<h:outputText value="Daily Statistic" />
							</f:facet>
							<rich:column>
							<f:facet name="header">
							<h:outputText value="Liquor Type"/>
							</f:facet>
							<h:outputText value="#{list.liqor_type }" />"
							</rich:column>
							
							
							
							<rich:column>
							<f:facet name="header">
							<h:outputText value="BL" />
							</f:facet>
							<h:outputText value="#{list.bl }" />
							</rich:column>
							
							
							
							
							<rich:column>
							<f:facet name="header">
							<h:outputText value="Duty" />
							</f:facet>
							<h:outputText value="#{list.duty }" />
							</rich:column>
							
							
							
							</rich:dataTable>
							<div class="col-md-12">
							<table>
							<tr>
							<td style="color:blue; font-weight: 15px;font-style: italic bold;">
							Total Deposit::
							</td>
							
							<td>
							#{dailyStatisticReportAction.totalDeposit}
							</td>
							</tr>
							</table>
							</div>
							
							
							<rich:spacer height="5px;"></rich:spacer>
							<rich:separator height="10px;" lineType="dashed"/>
							
							<rich:spacer height="10px;"></rich:spacer>
							
							
							
						<rich:dataTable var="list" value="#{dailyStatisticReportAction.dispatchToDistrict}" width="100%">
							<f:facet name="header">
							<h:outputText value="Dispatch To District Whole Sale" />
							</f:facet>
								<rich:column>
							<f:facet name="header">
							<h:outputText value="Liquor Type" />
							</f:facet>
							<h:outputText value="#{list.liqor_type }" />
							</rich:column>
							
							
							<rich:column>
							<f:facet name="header">
							<h:outputText value="Bl" />
							</f:facet>
							<h:outputText value="#{list.bl }" />
							</rich:column>
							
							
							<rich:column>
							<f:facet name="header">
							<h:outputText value="No.Of Cases" />
							</f:facet>
							<h:outputText value="#{list.no_of_cases }" />
							</rich:column>
							
							
							
							
							
							
							</rich:dataTable>
							
								<rich:spacer height="50px;"></rich:spacer>
							<rich:separator height="5px" lineType="dashed"></rich:separator>
							<rich:spacer height="10px;"></rich:spacer>
							
							<rich:dataTable var="list" value="#{dailyStatisticReportAction.dispatchFromDistrict}" width="100%">
							<f:facet name="header">
							<h:outputText value="Dispatch From District Whole Sale" />
							</f:facet>
							<rich:column>
							<f:facet name="header">
							<h:outputText value="Liquor Type" />
							</f:facet>
							<h:outputText value="#{list.liqor_type }" />
							</rich:column>
							
							<rich:column>
							<f:facet name="header">
							<h:outputText value="Bl" />
							</f:facet>
							<h:outputText value="#{list.bl }" />
							</rich:column>
							
							<rich:column>
							<f:facet name="header">
							<h:outputText value="No.Of Cases" />
							</f:facet>
							<h:outputText value="#{list.no_of_cases }" />
							</rich:column>
							
							
							
							
							
							
							
							
							</rich:dataTable>
							
							
							
							
							
							
							
							

</TD></TR></TABLE></div></div></h:form></f:view>
</ui:composition>