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
							<h:inputHidden value="#{stockAtWholeSellersAction.hidden}"></h:inputHidden>
						</div>
					</a4j:outputPanel>
				</div>

				<div class="row " align="center">
					<div align="center" width="100%" class="pghead">
						<h2>
							<h:outputText value="Stock at Wholesellers"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
						<center>
							<h:outputText value="#{stockAtWholeSellersAction.applicant_name}" />
						</center>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="18px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{stockAtWholeSellersAction.radio}">
							<f:selectItem itemValue="FL2" itemLabel="FL2" />
							<f:selectItem itemValue="FL2B" itemLabel="FL2B" />
							<f:selectItem itemValue="CL2" itemLabel="CL2" />

						</h:selectOneRadio>
					</div>
				</div>


				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row col-md-12" align="center">
					Between Dates :
					<rich:calendar value="#{stockAtWholeSellersAction.fromDate}"></rich:calendar>
					and :
					<rich:calendar value="#{stockAtWholeSellersAction.toDate}"></rich:calendar>
				</div>

				<rich:spacer height="20px"></rich:spacer>




				<div class="col-md=12" align="center">
					<h:outputText value="District Name : "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					<h:outputText value="#{stockAtWholeSellersAction.district}"
						rendered="#{stockAtWholeSellersAction.districtFlag eq false}">
					</h:outputText>
					<h:selectOneMenu
						rendered="#{stockAtWholeSellersAction.districtFlag eq true}"
						value="#{stockAtWholeSellersAction.distid}"
						style="height: 28px; width : 120px;">
						<f:selectItems value="#{stockAtWholeSellersAction.districtList}" />
					</h:selectOneMenu>
				</div>
				<br />

				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{stockAtWholeSellersAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{stockAtWholeSellersAction.pdfname}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{stockAtWholeSellersAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{stockAtWholeSellersAction.reset}">
					</h:commandButton>
				</div>
				<br />

			</div>
		</h:form>
	</f:view>
</ui:composition>