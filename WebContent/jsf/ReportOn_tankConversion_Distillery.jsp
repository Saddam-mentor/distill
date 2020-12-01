 <ui:composition
       xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

  <f:view>
     
       <h:form>
       
       
       <rich:spacer height="20px"></rich:spacer>
          <div>
              <h:messages errorStyle="color:red" style="background-color: white;font-size: 20px">
              </h:messages>
           </div>
           
           <div>
				<rich:spacer height="20px"></rich:spacer>
			</div>

			<div>
				<rich:separator lineType="dashed" />
			</div>
			<div>
				<rich:separator lineType="dashed" />
			</div>
			<div>
				<rich:spacer height="20px"></rich:spacer>
			</div>
       
           <div align="center" class="row">
          
               <h:outputText style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"
                       value="Report on Vat Conversion">
                       
               </h:outputText>         
           
           </div>
           <div>
				<rich:spacer height="10px"></rich:spacer>
			</div>

			<div>
				<rich:separator lineType="dashed" />
			</div>
			<div>
				<rich:separator lineType="dashed" />
			</div>
			<div>
				<rich:spacer height="20px"></rich:spacer>
			</div>
      
          
           
        
           
           
              <rich:spacer height="20px"></rich:spacer>
           <div class="col-md=12" align="center">
 
          
              <h:outputText>Form Date : </h:outputText>
             
             <rich:calendar value="#{reportOn_tankConversion_DistilleryAction.formdate}"></rich:calendar>
             <rich:spacer width="30px"></rich:spacer>
               <h:outputText>To Date : </h:outputText>
               <rich:calendar value="#{reportOn_tankConversion_DistilleryAction.todate}"></rich:calendar>
           
           </div>
           
             <rich:spacer height="20px"></rich:spacer>
           
   				<div class="row" align="center">
					<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{reportOn_tankConversion_DistilleryAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/Distillery/pdf/#{reportOn_tankConversion_DistilleryAction.printName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{reportOn_tankConversion_DistilleryAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt">
							</h:outputText>
					</h:outputLink>
          </div>
          </div>
           <rich:spacer height="20px"></rich:spacer>
            
           </h:form>
           </f:view>
</ui:composition>