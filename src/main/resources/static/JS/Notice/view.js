$(document).ready(()=>{
    $(".returnbtn").on("click",()=>{
            history.back();
    })

    $(".commentaddbtn").on("click", () => {
        $.ajax({
          url: '/commentadd',
          type: 'POST',
          contentType: 'application/json',
          data: JSON.stringify({
            userid: $(".userid").val(),
            nick: $(".nick").val(),
            comment: $(".commentadd").val(),
            noticeidx: $(".noticeidx").val()
          }),
          success: (data) => {
            const str = data.result.trim();
            console.log(str);
          },
          error: () => {
            alert('오류가 발생했습니다');
          }
        });
      });
      $(".commentdelete").on("click",(event)=>{
            const commentIdx = $(event.currentTarget).data("idx");
            console.log(commentIdx);
      })
      $(".commentmodify").on("click",(event)=>{
         const commentIdx = $(event.currentTarget).data("idx");
         console.log(commentIdx);
         const commentcontent =$(event.currentTarget).data("content");
         console.log(commentcontent);
         $("#" + commentIdx).html(
                `<input type="text" class="contentmodify" value="${commentcontent}">
                 <button type="button" class="commentmodifytrue" th:data-idx="${commentIdx}">수정</button>
                 <button type="button" class="commentmodifyreturn">취소</button>`
            );
      })


})