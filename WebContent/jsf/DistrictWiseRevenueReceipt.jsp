 <ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

   <f:view>

		<h:form>

			<TABLE width="80%">
				<TBODY>
					<TR>
						<TD align="left"><h3>
								<h:messages errorStyle="color:red" layout="table" id="messages"
									infoStyle="color:green" />
							</h3></TD>
					</TR>
				</TBODY>
			</TABLE>
       
                <div class="row " align="center">
				<rich:spacer height="10px"></rich:spacer>
			</div>
			<div class="row">
				<div align="center">
					<h:outputText value="Districtwise Revenue Receipt(in Cr.)"
						style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					<h:inputHidden value="#{districtWiseRevenueReceiptAction.hidden}"></h:inputHidden>
				</div>
			</div>
            <div class="row" align="center">
					<h:selectOneRadio value="#{districtWiseRevenueReceiptAction.radio }"
						onchange="this.form.submit();"
						valueChangeListener="#{districtWiseRevenueReceiptAction.radioListener}">
						<f:selectItem itemValue="D" itemLabel="Order by district" />
						<f:selectItem itemValue="M" itemLabel="Order by Month Target" />
						<f:selectItem itemValue="U" itemLabel="Order by Upto Month Target" />
						<f:selectItem itemValue="P" itemLabel="Order by Month's Target %" />
					</h:selectOneRadio>
				</div>
                      
          <hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
          
          <div class="col-md-2" align="right">
						<h:outputLabel value=" Month :"
							style="FONT-FAMILY: 'cooperBlack'; COLOR: #c40000; font-weight:bold" />
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu style="width: 250px; height: 28px;"
							value="#{districtWiseRevenueReceiptAction.finanMonth}"
							onchange="this.form.submit();"
							valueChangeListener="#{districtWiseRevenueReceiptAction.changelis}"
							styleClass="dropdown-menu">
							<f:selectItem itemValue="0" itemLabel="--Select--" />
							<f:selectItem itemValue="4" itemLabel="April" />
							<f:selectItem itemValue="5" itemLabel="May" />
							<f:selectItem itemValue="6" itemLabel="June" />
							<f:selectItem itemValue="7" itemLabel="July" />
							<f:selectItem itemValue="8" itemLabel="August" />
							<f:selectItem itemValue="9" itemLabel="September" />
							<f:selectItem itemValue="10" itemLabel="October" />
							<f:selectItem itemValue="11" itemLabel="November" />
							<f:selectItem itemValue="12" itemLabel="December" />
							<f:selectItem itemValue="1" itemLabel="January" />
							<f:selectItem itemValue="2" itemLabel="February" />
							<f:selectItem itemValue="3" itemLabel="March" />

						</h:selectOneMenu>
					</div>
          <div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
				
				<div class="row" align="center">
					<h:commandButton value="Print Report" action="#{districtWiseRevenueReceiptAction.print }">
					</h:commandButton>
					
					<h:outputLink styleClass="outputLinkEx" value="/doc/ExciseUp/MIS/pdf/#{districtWiseRevenueReceiptAction.pdfname}"
									target="_blank">
					<h:outputText styleClass="outputText" id="text223" value="View Report"
					rendered="#{districtWiseRevenueReceiptAction.printFlag==true}"
					style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					
				</div>	
			
			<div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
			
				
       </h:form>

    </f:view>
          			
</ui:composition>