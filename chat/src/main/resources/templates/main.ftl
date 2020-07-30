<#import "parts/common.ftl" as common>

<@common.page>
    <div class="form-row text-center">
        <div class="col-12">
            <a class="btn btn-info mb-3" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">Add new message</a>
        </div>
    </div>

    <div class="collapse <#if message??>show</#if>" id="collapseExample">
            <form method="post" enctype="multipart/form-data">
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <input class="form-control ${(textMessageError??)?string('is-invalid','')}" type="text" value="<#if message??>${message.textMessage}</#if>" name="textMessage" placeholder="Input text">
                            <#if textMessageError??>
                                <div class="invalid-feedback">
                                    ${textMessageError}
                                </div>
                            </#if>
                        </div>
                        <div class="col">
                            <div class="custom-file">
                                <input type="file" name="file" id="customFile">
                            </div>
                        </div>
                    </div>
                <div class="form-row text-center">
                    <div class="col-12">
                        <button class="btn btn-success mt-3 btn-lg text-center" type="submit">Add</button>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>

    </div>

    <div class="row">
        <#list messages as message>
            <div class="col-md-6 col-xl-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <i>${message.textMessage}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.getAuthorName()}
                </div>
            </div>
        </#list>
    </div>


</@common.page>

