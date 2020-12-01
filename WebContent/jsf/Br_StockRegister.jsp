<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">

			<div class="form-group">
				<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>

				<div class="row " align="center">
					<div align="center" width="100%" class="pghead">
					<h:inputHidden
									value="#{brewery_Stock_RegisterAction.hidden}"></h:inputHidden>
						<h2>
							<h:outputText value=" Stock Report "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
					<div>
					<h:outputLabel
									value="#{brewery_Stock_RegisterAction.applicant_name}"
									style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel>
					</div>
					
					
				</div>
				
				

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="col-md-12" align="center">
					<div class="col-md-5" align="right">
						<h:outputText value="Select Brand Name:"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu
							
							value="#{brewery_Stock_RegisterAction.packId}"
							style="width: 250px; height: 28px;" >
							<f:selectItems
								value="#{brewery_Stock_RegisterAction.brandList}" />
						</h:selectOneMenu>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<div class="col-md-3"></div>
					<div class="col-md-1" align="right">From:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{brewery_Stock_RegisterAction.fromDate}">
						</rich:calendar>
					</div>
					<div class="col-md-1" align="right">To:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{brewery_Stock_RegisterAction.toDate}">
						</rich:calendar>
					</div>
					<div class="col-md-3"></div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{brewery_Stock_RegisterAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{brewery_Stock_RegisterAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{brewery_Stock_RegisterAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{brewery_Stock_RegisterAction.reset}">
					</h:commandButton>
				</div>
				<br />
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>