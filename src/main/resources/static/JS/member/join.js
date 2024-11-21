 $(document).ready(() => {
     Kakao.init('700276f2395c015b65b2f3a862959e0f');

     //돌아가기
     $(".returnbtn").on("click",()=>{
             window.location.href = '/';
         })

     // 주소 검색 기능
     $(".searchAddress").on("click", () =>{
         new daum.Postcode({
             oncomplete: (data) => {
                 $("#postnumber").val(data.zonecode);
                 $("#street").val(data.address);
             }
         }).open();
     });

     // 카카오 로그인 기능
     $("#loginWithKakao").on("click",async () => {
        try{
                const authObj = await Kakao.Auth.login();
                const res = await Kakao.API.request({url: '/v2/user/me'});
                $('#nick').val(res.kakao_account.profile.nickname);
           }
        catch(err)
        {
            console.error(err);
        }
     });

     const ERROR_MESSAGES = {
         FORMAT: "<font color='red'>형식에 맞지않습니다</font>",
         DUPLICATE_ID: "<font color='red'>중복되었습니다</font>",
         MISMATCH_ID: "<font color='red'>일치하지 않습니다</font>",
         AVAILABLE_ID: "<font color='blue'>사용가능합니다</font>",
         DEFAULT: "<font>입력해주세요</font>"
     };

     // 실시간 체크 함수
     const checkField = (url, data, successCallback, errorCallback) => {
         $.ajax({
             url: url,
             method: 'POST',
             contentType: 'application/json',
             data: JSON.stringify(data),
             dataType: 'json',
             success: successCallback,

         });
     };

     // 아이디 실시간 체크
     $(".check-userid").on("keyup", () => {
         checkField('/userid_check', { userid: $(".check-userid").val() }, (data) => {
             console.log(data);
             const str = data.result.trim();
             const messages = {
                 "C": ERROR_MESSAGES.FORMAT,
                 "N": ERROR_MESSAGES.DUPLICATE_ID,
                 "Y": ERROR_MESSAGES.AVAILABLE_ID,
                 "default": ERROR_MESSAGES.DEFAULT
             };
             $('#idch').html(messages[str] || messages["default"]);
             $('#useridcheck').val(str === "Y" ? "true" : (str === "C" ? "falsec" : (str === "N" ? "falsen" : "false")));
         });
     });

     // 비밀번호 실시간 체크
     $(".check-pwd").on("keyup", () => {
         checkField('/pwd_check', { pwd: $("#pwd").val(), pwdch: $("#pwdch").val() }, (data) => {
             const str = data.result.trim();
             const messages = {
                 "C": ERROR_MESSAGES.FORMAT,
                 "N": ERROR_MESSAGES.MISMATCH_ID,
                 "Y": ERROR_MESSAGES.AVAILABLE_ID,
                 "default": ERROR_MESSAGES.DEFAULT
             };
             $('#pwdche').html(messages[str] || messages["default"]);
             $('#pwdcheck').val(str === "Y" ? "true" : (str === "C" ? "falsec" : (str === "N" ? "falsen" : "false")));
         });
     });

     // 닉네임 실시간 체크
     $(".check-nick").on("keyup", () => {
         checkField('/nick_check', { nick: $(".check-nick").val() }, (data) => {
             const str = data.result.trim();
             const messages = {
                 "C": ERROR_MESSAGES.FORMAT,
                 "N": ERROR_MESSAGES.DUPLICATE_ID,
                 "Y": ERROR_MESSAGES.AVAILABLE_ID,
                 "default": ERROR_MESSAGES.DEFAULT
             };
             $('#nickch').html(messages[str] || messages["default"]);
             $('#nickcheck').val(str === "Y" ? "true" : (str === "C" ? "falsec" : (str === "N" ? "falsen" : "false")));
         });
     });
    const validateForm = () => {
        const checks = [
            { check: $("#useridcheck").val(), message: "아이디를 입력해주세요", field: "#userid" },
            { check: $("#pwdcheck").val(), message: "비밀번호를 입력해주세요", field: "#pwd" },
            { check: $("#nickcheck").val(), message: "닉네임을 입력해주세요", field: "#nick" },
        ];

        for (const { check, message, field } of checks) {
            if (check === "false") {
                alert(message);
                $(field).focus();
                return false;
            }
        }

        const requiredFields = [
            { field: "#name", message: "이름을 입력해주세요" },
            { field: "#email", message: "이메일을 입력해주세요" },
            { field: "#postnumber", message: "주소를 검색해주세요", focusField: "#addressbtn" },
            { field: "#address", message: "나머지주소를 입력해주세요" },
        ];

        for (const { field, message, focusField } of requiredFields) {
            if ($(field).val() === "") {
                alert(message);
                $(focusField || field).focus();
                return false;
            }
        }

        if ($("#email2").val() === "other" && $("#email3").val() === "") {
            alert("이메일의 도메인을 입력해주세요");
            $("#email3").focus();
            return false;
        }

        const icon = $("#icon").val();
        if (!icon.endsWith("jpg") && !icon.endsWith("jpeg") && !icon.endsWith("png") && icon!="") {
            alert("아이콘의 형식이 맞지않습니다");
            return false;
        }

        return true;
    };

     // 가입완료를 눌렀을때 체크
     $(".onsubmit").on("submit", (event) => {
         event.preventDefault();
            if (validateForm()) {
                $(".onsubmit").off("submit").submit();
             }
     });

     // 이메일 도메인 체크
     $("#email2").on("change", () => {
         if ($("#email2").val() === "other") {
             $("#emailSelectDiv").css("display", "none");
             $("#option").css("display", "block");
         }
     });

     $(".emailbtn").on("click", () => {
         $("#emailSelectDiv").css("display", "block");
         $("#option").css("display", "none");
     });
 });
