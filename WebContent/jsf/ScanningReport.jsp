<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
<f:view>
<h:form>
	
  <div class="row " align="center">
					<div align="center" width="100%" class="pghead">
						<h2>
							<h:outputText value="इन्टीग्रेटेड एक्साइज सप्लाई चेन मैनेजमैंट सिस्टम के क्रियान्वयन हेतु मदिरा की बोतल स्कैनिंग से प्राप्त धनराशी"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>

					</div>
				</div>
				<div>
					<a4j:outputPanel id="msg">
						<h:messages errorStyle="color:red"
							style="font-size: 14px;font-weight: bold"
							styleClass="generalExciseStyle" layout="table" id="messages"
							infoStyle="color:green">
						</h:messages>
					</a4j:outputPanel>
				</div>
				<rich:spacer height="5" ></rich:spacer>
				<rich:separator lineType="dashed"></rich:separator>
				
				<div class="row" align="center">
				<div class="col-md-2"></div>
				<div class="col-md-2" align="right">
				<h:outputText value="From Date" ></h:outputText>
				</div>
				<div class="col-md-2">
				<rich:calendar value="#{scanningReportAction.fromDate }"></rich:calendar>
				</div>
				<div class="col-md-1" align="right">
				<h:outputText value="To Date"></h:outputText>
				</div>
				<div class="col-md-2" align="left">
				<rich:calendar value="#{scanningReportAction.toDate }"></rich:calendar>
				</div>
				<div class="col-md-3"></div>
				</div>
				
				<rich:separator lineType="dashed"></rich:separator>
				<rich:spacer height="10"></rich:spacer>
				
				<div align="center">
				
				<div class="col-md-4"></div>
				<div class="col-md-1"></div>
				<div class="col-md-1">
				
				<h:commandButton value="Print Report" action="#{scanningReportAction.printReport}" styleClass="btn btn-primary"></h:commandButton>
				
				</div>
				
				
					<div class="col-md-1">
					<h:commandButton value="Reset" action="#{scanningReportAction.reset }" styleClass="btn btn-danger"></h:commandButton>
					
					</div>
					<div class="col-md-1">
				<h:outputLink value="#{scanningReportAction.url}" target="_blank"  rendered="#{scanningReportAction.printFlag }">
				
				<h:outputText value="Download Report" />
				</h:outputLink>
				
				</div>
				<div class="col-md-4"></div>
				</div>
				</h:form>
				</f:view>
</ui:composition>