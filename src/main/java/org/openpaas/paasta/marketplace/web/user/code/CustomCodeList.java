package org.openpaas.paasta.marketplace.web.user.code;

import java.util.List;

import org.openpaas.paasta.marketplace.web.user.common.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomCodeList extends BaseModel {

	private List<CustomCode> items;

}
