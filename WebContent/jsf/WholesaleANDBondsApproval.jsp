<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>

		<h:form style="background-color:whitesmoke;">
			<head>
<style>
.striped-border {
	border: 1px dashed #000;
	width: 50%;
	margin: auto;
	margin-top: 5%;
	margin-bottom: 5%;
}

.example3 {
	border: 0;
	height: 1px;
	background: #000066;
}
.button1 {
  background-color: white;
  color: black;
  border: 2px solid #4CAF50; /* Green */
}
</style>


			</head>
			<div class="form-group">
				<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red" style="font-size: 18px;"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">

					<div align="center">
						<h:outputText value="Wholesale AND Bonds Approval"
							style=" text-shadow: 2px 2px 4px #000000;   FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>

					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="col-md-12" align="center"
					style="BACKGROUND-COLOR: beige;">
					<h:selectOneRadio
						style="FONT-WEIGHT: bold;  font-size: 18px; "
						value="#{wholesaleANDBondsApprovalAction.radio_type}"
						valueChangeListener="#{wholesaleANDBondsApprovalAction.radioVal}"
						onchange="this.form.submit();">
						<f:selectItem itemValue="UP" itemLabel="UPBonds" />
						<f:selectItem itemValue="W" itemLabel="Wholesale" />



					</h:selectOneRadio>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="col-md-12" align="center"
					style="BACKGROUND-COLOR: beige;">
					<h:selectOneRadio  rendered="#{wholesaleANDBondsApprovalAction.radioView eq 'F'}"
					 value="#{wholesaleANDBondsApprovalAction.radioS}"
						onchange="this.form.submit();">
						<f:selectItem itemValue="S" itemLabel="Summary" />
						<f:selectItem itemValue="D" itemLabel="Detail" />



					</h:selectOneRadio>
				</div>
				  <rich:spacer height="10px"></rich:spacer>
           
   				<div class="row" align="center"> 
					<h:commandButton value="Print Report" styleClass="btn-sm btn-primary"
					style="  background-color: white;color: black;border: 2px solid #4CAF50"
						action="#{wholesaleANDBondsApprovalAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{wholesaleANDBondsApprovalAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{wholesaleANDBondsApprovalAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt">
							</h:outputText>
							<rich:spacer width="10px"></rich:spacer>
					</h:outputLink>
					
          </div>
            <rich:spacer height="10px"></rich:spacer>
          	<div class="col-md-12" align="center">
					<h:commandButton styleClass="btn-sm btn-primary"
						action="#{wholesaleANDBondsApprovalAction.excel}" value="Generate Excel"
						rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{wholesaleANDBondsApprovalAction.exlname}.xlsx"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{wholesaleANDBondsApprovalAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					<rich:spacer width="20px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn-sm btn-warning"
						action="#{wholesaleANDBondsApprovalAction.reset}">
					</h:commandButton>
				</div>
				<rich:spacer width="20px;"></rich:spacer>
			</div>
		</h:form>
	</f:view>

</ui:composition>