(function ($) {
    //    "use strict";
    /*  Data Table
    -------------  chistory*/
	
   $('#vendorTable').DataTable({
        lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
        "order": [[0, "desc"]],
    });
   $('#customerTable').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#employeeTableId').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#i9fromTable').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#i9fromTable2').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#supportTable').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#otherdocTable').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#historyid1').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   $('#historyid2').DataTable({
       lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]],
   });
   
})(jQuery);