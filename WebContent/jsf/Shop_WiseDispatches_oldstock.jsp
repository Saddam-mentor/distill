
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
					<div>
						<h:inputHidden value="#{shop_WiseDispatchesAction.hidden}" />
						<h2>
							<h:outputText
								value="Shop Wise Dispatches Old_Stock 2018_19  "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;">
							</h:outputText>
						</h2>
					</div>
				</div>



				<div class="form-group newsdiv">

					<rich:spacer height="20px;"></rich:spacer>

					<div class="row col-md-12" align="center">
						From Date :
						<rich:calendar value="#{shop_WiseDispatchesAction.fromdate}"></rich:calendar>
						To Date :
						<rich:calendar value="#{shop_WiseDispatchesAction.todate}"></rich:calendar>
					</div>
					<div>
						<rich:spacer height="30px" />
					</div>

					<div class="row col-md-12" align="center">

						District:
						<h:selectOneMenu onchange="this.form.submit()"
							valueChangeListener="#{shop_WiseDispatchesAction.issueSpiritsVAT}"
							value="#{shop_WiseDispatchesAction.districId}"
							style="width: 250px; height: 28px;">


							<f:selectItems value="#{shop_WiseDispatchesAction.districList}" />



						</h:selectOneMenu>


					</div>

					<div>
						<rich:spacer height="30px" />
					</div>


					<div class="row col-md-12" align="center">
						<h:selectOneRadio onclick="this.form.submit();"
							value="#{shop_WiseDispatchesAction.radio}"
							valueChangeListener="#{shop_WiseDispatchesAction.radioListiner}">
							<f:selectItem itemValue="Model Shop" itemLabel="Model Shop" />
							<f:selectItem itemValue="Foreign Liquor"
								itemLabel="Foreign Liquor" />
							<f:selectItem itemValue="Beer" itemLabel="Beer" />

						</h:selectOneRadio>
					</div>


					<rich:spacer height="20px" />

					<div class="row col-md-12" align="center">

						Shop Name:
						<h:selectOneMenu
							valueChangeListener="#{shop_WiseDispatchesAction.shopname}"
							value="#{shop_WiseDispatchesAction.shopId}"
							style="width: 250px; height: 28px;">


							<f:selectItems value="#{shop_WiseDispatchesAction.shopList}" />



						</h:selectOneMenu>


					</div>
					<div>
						<rich:spacer height="30px" />
					</div>

					<div class="col-md-12" align="center">
						<h:commandButton action="#{shop_WiseDispatchesAction.print}"
							value="Print Report"
							style="background-color:#C5C5C5; font-size: large;"
							rendered="true" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/WholeSale/pdf/#{shop_WiseDispatchesAction.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{shop_WiseDispatchesAction.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
					<div class="row " align="center">
						<rich:spacer height="10px"></rich:spacer>
					</div>
					<div class="col-md-12" align="center">
						<h:commandButton action="#{shop_WiseDispatchesAction.excel}"
							value="Generate Excel"
							style="background-color:#C5C5C5; font-size: large;"
							rendered="true" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//WholeSale//Excel//#{shop_WiseDispatchesAction.exlname}.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{shop_WiseDispatchesAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
					</div>





					<div class="row " align="center">
						<rich:spacer height="20px"></rich:spacer>
					</div>







					<div class="row " align="center">
						<rich:spacer height="20px"></rich:spacer>
					</div>
					<div class="col-md-12" align="center">

						<h:commandButton action="#{shop_WiseDispatchesAction.reset}"
							value="Reset"
							style="background-color:#C5C5C5; font-size: large; width: 110px;" />

					</div>
					<rich:spacer height="20px" />

				</div>
			</div>
		</h:form>
	</f:view>
</ui:composition>