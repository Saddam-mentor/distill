<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">


	<h:form>
		<f:view>
			<rich:spacer height="20px;"></rich:spacer>
			
            <div style="width : 70%; margin : auto; padding: 10px; border : 3px solid navy; margin-top: 20px; margin-bottom : 20px">
			<div style="width: 100%; height: 35px;">
				<div style="padding-top: 2px; padding-bottom: 2px; margin-top: 2px;"
					align="center">
					<h:outputText value="Brand Wise Registration"
						styleClass="generalExciseStyle1"
						style="font-weight: bold; text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:35px;"></h:outputText>
				</div>

			</div>
			<br />
			<br />


			<rich:spacer height="30px;"></rich:spacer>

			<div class="row" align="center">
				<h:commandButton action="#{unitAction.print}"
					style="background-color:#C5C5C5; font-size: large;"
					value="Generate PDF"></h:commandButton>
				<br /> <br />
			</div>
			<div class="row" align="center">

				<h:outputLink styleClass="outputLinkEx" id="linkEx2"
					value="/doc/ExciseUp/reports/#{unitAction.printName}"
					target="_blank">
					<h:outputText styleClass="outputText" id="text223"
						value="View Report" rendered="#{unitAction.printFlag==true}"
						style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
				</h:outputLink>
			</div>

			<div class="row" align="center">

				<h:commandButton action="#{unitAction.excel}" value="Generate Excel"
					style="background-color:#C5C5C5; font-size: large;" />
				<br /> <br />
			</div>

			<div class="row" align="center">

				<h:outputLink styleClass="outputLinkEx"
					value="/doc/ExciseUp/WholeSale/Excel/#{unitAction.exlname}Report.xls"
					target="_blank">
					<h:outputText styleClass="outputText" value="Download Excel"
						rendered="#{unitAction.excelFlag==true}"
						style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
				</h:outputLink>

			</div>
			</div>
		</f:view>

	</h:form>


</ui:composition>