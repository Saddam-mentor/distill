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
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">
					<div align="center">
						<h:outputText value="FL1 Stock Register Report"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
							<h:inputHidden value="#{fL1DistileryStockRegisterAction.hidden}"></h:inputHidden>
					</div>
				</div>
				
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>


				<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{fL1DistileryStockRegisterAction.loginUserNm}"
							style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">
					<div align="center">
						<h:outputLabel
							value="#{fL1DistileryStockRegisterAction.loginUserAdrs}"
							style="COLOR: #000000; FONT-SIZE: medium;"></h:outputLabel>
					</div>
				</div>

				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>

				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">
					Between Dates :
					<rich:calendar value="#{fL1DistileryStockRegisterAction.fromDate}"></rich:calendar>
					and :
					<rich:calendar value="#{fL1DistileryStockRegisterAction.toDate}"></rich:calendar>
				</div>




				<div class="row " align="center">
					<rich:spacer height="40px"></rich:spacer>
				</div>

				<div class="row" align="center">
					<div class="col-md-1"></div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Brand:"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu
							value="#{fL1DistileryStockRegisterAction.brandId}"
							style="height: 25px; width : 300px;">
							<f:selectItems
								value="#{fL1DistileryStockRegisterAction.brandList}" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select License Number :"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu
							value="#{fL1DistileryStockRegisterAction.licenseNo}"
							style="width: 250px; height: 28px;">
							<f:selectItems
								value="#{fL1DistileryStockRegisterAction.fL1LicenseList}" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-1"></div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<br /> <br />
				<div class="row" align="center">

					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{fL1DistileryStockRegisterAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{fL1DistileryStockRegisterAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{fL1DistileryStockRegisterAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{fL1DistileryStockRegisterAction.reset}">
					</h:commandButton>
				</div>


				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>