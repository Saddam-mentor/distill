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
		.table{
		 background-color:#deeaee ;
		
		}
		.TableHead
		{
		 background-color:#92a8d1 ;
		}
		
.button {
  border: 1px solid black;
  background-color: white;
  color: black;
  padding: 5px 14px;
  font-size: 13px;
  cursor: pointer;
}
.success {
background-color: lightgreen;
  border-color: #4CAF50;
   border-radius: 3px;
}
.warning {
  border-color: #ffad33;
  background-color:lightyellow;
  border-radius: 3px;
}

hr.style-one {
border: 0;
height: 1px;
width :80% ;
background: #333;
background-image: -webkit-linear-gradient(left, #ccc, #333, #ccc);
background-image: -moz-linear-gradient(left, #ccc, #333, #ccc);
background-image: -ms-linear-gradient(left, #ccc, #333, #ccc);
background-image: -o-linear-gradient(left, #ccc, #333, #ccc);
}
.inputtext {
	border-radius: 6px;
	padding: 5px 5px;
	height: 30px;
	width: 250px;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}	
</style>
<link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.0.0/animate.min.css" />		
			
		
		</head>
			<div class="form-group">
				<div class="row" align="center">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 18px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="animate__animated animate__bounce" align="center">
					<div style="padding: 10px 0px;  background-color:#92a8d1 ;">
						
						<h2>
							<h:outputText
								value="Monthwise Production And Export(Outside U.p + Outside Country) Of Alcohol"
								styleClass="generalHeaderOutputTable"
								style="FONT-SIZE: x-large; FONT-FAMILY: 'Microsoft Sans Serif'; color:white ;font-weight: bold;margin-top: 2px;letter-spacing:2px;">
							</h:outputText>
						</h2>
					</div>
				</div>
			   <hr class="style-one"/>
               	
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
</div>

                    <div class="row" align="center">
                     <div class="col-md-2"></div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Year :"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu 
							value="#{monthwisereportonproductionandconsumptionofalcohol_action.yearr}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						    <f:selectItems
							value="#{monthwisereportonproductionandconsumptionofalcohol_action.year}"/>
						</h:selectOneMenu>
					</div>
					<div class="col-md-2" align="right">
						<h:outputText value="Select Month :"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu
							value="#{monthwisereportonproductionandconsumptionofalcohol_action.montth}"
						    style="height: 25px; width : 150px;" onchange="this.form.submit();">
						   <f:selectItems
							value="#{monthwisereportonproductionandconsumptionofalcohol_action.month}"/>
						</h:selectOneMenu>
					</div>
				  <div class="col-md-2"></div>
				</div>
			  	<rich:spacer height='20px'></rich:spacer>
			
			   <div class="col-md-12" align="center">


						<h:commandButton
							action="#{monthwisereportonproductionandconsumptionofalcohol_action.print}"
							value="Print Report"
							styleClass="btn btn-success btn-sm"/>

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/MIS/pdf/#{monthwisereportonproductionandconsumptionofalcohol_action.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{monthwisereportonproductionandconsumptionofalcohol_action.printFlag}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>
					<div class="col-md-12" align="center">


						<h:commandButton
							action="#{monthwisereportonproductionandconsumptionofalcohol_action.excel}"
							value="Generate Excel"
							styleClass="btn btn-success btn-sm" rendered="true" />

						<h:outputLink styleClass="outputLinkEx"
							
							value="/doc/ExciseUp/MIS/Excel/#{monthwisereportonproductionandconsumptionofalcohol_action.exlname}.xlsx"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{monthwisereportonproductionandconsumptionofalcohol_action.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
                     </div>
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>
					<div class="col-md-12" align="center">
                     <h:commandButton
							action="#{monthwisereportonproductionandconsumptionofalcohol_action.reset}" value="Reset"
							styleClass="btn btn-warning btn-sm"/>
                        </div>
					<rich:spacer height="20px"/>
				  </h:form>
				</f:view>
</ui:composition>