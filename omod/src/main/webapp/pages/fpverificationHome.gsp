<% ui.decorateWith("appui", "standardEmrPage") %>

<%= ui.resourceLinks() %>

<div class="row wrapper white-bg page-heading" style="display: flex; justify-content: center; align-items: center;">
    <h4 style="text-align: center;">
        NDR Export for fingerprint recapture
    </h4>
</div>


<div class="container" id="container" style="padding-top: 10px;">
    <div style="margin-left: 28%; width: 50%; height: 50%; background-color: #00463f; border-radius: 10px; " id="customDiv">
        <br/> <br/>
        <div style="padding-left: 38px">
            <label for="custom" style="font-weight: bold; color: white; cursor: pointer;">
                <input style="background-color: #E8F0FE; border-radius: 10px; margin-top: 15px; cursor: pointer" type="checkbox" id="custom" name="custom" value="custom" onclick="checkBoxCheck()">
                Custom
            </label>
            <br id="br1">
            <input style="background-color: #E8F0FE; width: 85%; height: 45px; border-radius: 10px; margin-top: 15px; padding-left: 18px; padding-right: 10px;  display:none;" type="text" value="comma separated patient ART Identifiers" id="identifiers" onfocus=this.value='' name="identifiers"><br id="br2">
            <br id="br3"/>
            <div style="display: flex;">
                <div style="width: 45%">
                    <label id="lblfrom" name="startdate" for="from" style="font-weight: bold; color: white;">Start Date</label><br id="br4">
                    <input style="font-weight: bold;padding-left: 10px; padding-right: 10px; background-color: #E8F0FE; margin-bottom: 15px; width: 85%; height: 45px; border-radius: 10px; margin-top: 15px;" id="startdate" name="startdate" type="date" required="required"/><br id="br5">
                </div>
                <div style="width: 48%; margin-left: 5%">
                    <label id="lblto" for="to" style="font-weight: bold; color: white;">End Date</label><br id="br6">
                    <input style="font-weight: bold;padding-left: 10px; padding-right: 10px; background-color: #E8F0FE; width: 87%; height: 45px; border-radius: 10px; margin-top: 15px;" id="enddate" name="enddate" type="date" required="required"/><br id="br7">
                </div>
            </div>
            <br/>
            <div style="display: flex; justify-content: center;"> <!-- Center the button -->
                <div style="width: 75%">
                    <input style="font-weight: bold; padding-left: 10px; padding-right: 10px; background-color: #E8F0FE; width: 93%; height: 45px; border-radius: 10px; margin-top: 15px;" type="button" value="Export" onclick="getStart()" id="exportData" class="btn btn-primary" />
                </div>
            </div>
        </div>
        <br/>
        <br/>
    </div>
    <br/>
    <div class="table-responsive">
        <table class="table table-striped table-bordered  table-hover" id="tb_commtester">
            <thead>
            <tr>
                <th>${ ui.message("File Name") }</th>
                <th>${ ui.message("Date Started") }</th>
                <th>${ ui.message("Date Completed") }</th>
                <th>${ ui.message("Total No. of Patients") }</th>
                <th>${ ui.message("Actions") }</th>
            </tr>

            </thead>
            <tbody id="TableBody">

            </tbody>
        </table>
    </div>
    <div id="gen-wait" class="dialog" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 9999;">
        <div class="row">
            <div class="col-md-3 col-xs-3 offset-2" >
                <img src="../moduleResources/fpverification/images/Sa7X.gif" alt="Loading Gif"  style="width:100px">
            </div>
        </div>

        <div>
            <div class="col-md-7 col-xs-7 " style="text-align:center;">
                <h1>Please wait, operation in progress...</h1>
            </div>
        </div>
    </div>
</div>

<script>
    var jq = jQuery;

    function getStart() {
        // Show 'gen-wait' element when processing starts.
        jq('#gen-wait').show();

        console.log("Started Job");
        var startdate = document.getElementById("startdate").value;
        var enddate = document.getElementById("enddate").value;
        let patientidentifiers = document.getElementById("identifiers").value;

        /*if(identifiers === "comma separated patient identifiers or Ids")
            identifiers = '';*/

        // Check if either startdate or enddate is empty
        if(startdate === "" || enddate === ""){
            alert("Please provide both start date and end date");
            // Hide 'gen-wait' element since there's no processing happening
            jq('#gen-wait').hide();
            return;
        }

        console.log(startdate);
        console.log(enddate);

        if (patientidentifiers === "Comma Separated Patient ART Identifiers" || (jq('#custom').prop('checked') && patientidentifiers === "")) {
            alert("Please enter the patient identifiers separated with comma");
            // Hide 'gen-wait' element since there's no processing happening
            jq('#gen-wait').hide();
            return;
        }

        jq.ajax({
            url: "${ ui.actionLink("fpverification", "fpverificationHome", "extractFingerprint") }",
            dataType: "json",
            data:{
                'startdate':startdate,
                'enddate':enddate,
                'patientidentifiers' : patientidentifiers,
            },
            success: function (response) {
                // Hide 'gen-wait' element when a response is received.
                jq('#gen-wait').hide();
                console.log(response);
                var res = JSON.parse(response);
                if(res === "No record found") {
                    alert(res);
                } else {
                    console.log(res);
                    jq('#TableBody')
                        .append("<tr>" +
                            "<td>" + res[0] + "</td>" +
                            "<td>" + res[1] + "</td>" +
                            "<td>" + res[1] + "</td>" +
                            "<td>" + res[2] + "</td>" +
                            "<td><a href='" + res[3] + "' download>Download</a></td>" +
                            "</tr>");
                }
            },
            error:function(xhr){
                // Hide 'gen-wait' element if an error occurs.
                jq('#gen-wait').hide();
                console.log(xhr);
            }
        });
    }

</script>


<script>
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("startdate").setAttribute("max", today);
    document.getElementById("enddate").setAttribute("max", today);

    let isCheckedBefore = false;

    function checkBoxCheck() {
        const checkBox = document.getElementById("custom");
        const identifiersInput = document.getElementById('identifiers');

        if (checkBox.checked === true) {
            identifiersInput.style.display = 'inline';
            isCheckedBefore = true;
        } else {
            identifiersInput.style.display = 'none';
            if (isCheckedBefore) {
                identifiersInput.value = ''; // Clear the input value
                isCheckedBefore = false;
            }
        }
    }

</script>
