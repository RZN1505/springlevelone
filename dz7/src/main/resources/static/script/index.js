
$(document).ready(function(){
    $('.filterable .btn-filter').click(function(){
        var $panel = $(this).parents('.filterable'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });

    $('.filterable .filters input.form-control').keyup(function(e){
               /* Ignore tab key */
               var code = e.keyCode || e.which;
               if (code == '9') return;
               /* Useful DOM data and selectors */
               var $input = $(this),
               respParams = {},
                $inputs = $('.filterable input.form-control');
                 $inputs.each( (key, item) => {
                 var inputContent = $(item).val().toLowerCase() || "",
                             inputDataAttr = $(item).data('attr');
                             respParams[inputDataAttr] = inputContent;
                 });
               $panel = $input.parents('.filterable'),
               column = $panel.find('.filters th').index($input.parents('th')),
               $table = $panel.find('.table'),
               $rows = $table.find('tbody tr.rows');
               /* Dirtiest filter function ever ;) */
               $.ajax({
                       type: "POST",
                       contentType: "application/json",
                       url: "/products-rest/filter",
                       data: JSON.stringify(respParams),
                       dataType: 'json',
                       cache: false,
                       timeout: 600000,
                       success: function (data) {
                           console.log("SUCCESS : ", data);
                           const len = data.length;
                         //  $rows.splice(len, $rows.length-len);
                        $rows.each((key,val) => {
                        if((key + 1) <=len){
                        $(val).show();
                        let cols = $(val).find('input');
                                          cols.each( (id, col) => {
                                          let columnAttr = $(col).data('attr');
                                            $(col).val(data[key][columnAttr])
                                          })
                        } else {
                         $(val).hide();
                        }
                        })
                       },
                       error: function (e) {
                           console.log("ERROR : ", e);

                       }
                   });
           });

           $('.rows input.form-control-edit').keyup(function(e){
                   /* Ignore tab key */
                   var code = e.keyCode || e.which;
                   if (code == '9') return;
                   /* Useful DOM data and selectors */
                   var $input = $(this),
                   $parent = $(this).parent().parent();
                   let respParams = {},
                    $inputs = $parent.find('input');
                     $inputs.each( (key, item) => {
                     var inputContent = $(item).val().toLowerCase() || "",
                                 inputDataAttr = $(item).data('attr');
                                 respParams[inputDataAttr] = inputContent;
                     });

                  // var $rows = $table.find('tbody tr.rows');
                   /* Dirtiest filter function ever ;) */
                   $.ajax({
                           type: "POST",
                           contentType: "application/json",
                           url: "/products-rest/update",
                           data: JSON.stringify(respParams),
                           dataType: 'json',
                           cache: false,
                           timeout: 600000,
                           success: function (data) {
                               console.log("SUCCESS : ", data);
                               const len = data.length;
                             //  $rows.splice(len, $rows.length-len);
                            let cols = $parent.find('input');
                                              cols.each( (id, col) => {
                                              let columnAttr = $(col).data('attr');
                                                $(col).val(data[columnAttr])
                                              })
                           },
                           error: function (e) {
                               console.log("ERROR : ", e);

                           }
                       });
               });
});