package com.yst.onecity.activity.addorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.addorder.InvoiceBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.dialog.InvoiceHelpDialog;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发票信息页面
 *
 * @author jiaofan
 * @version 4.0.0
 * @date 2018/3/20
 */
public class InvoiceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tv_main_title)
    TextView mTvMainTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.rb_invoice_myself)
    RadioButton mRbInvoiceMyself;
    @BindView(R.id.rb_invoice_company)
    RadioButton mRbInvoiceCompany;
    @BindView(R.id.rg_invoice)
    RadioGroup mRgInvoice;
    @BindView(R.id.et_invoice_myself)
    EditText mEtInvoiceMyself;
    @BindView(R.id.ll_invoice1)
    LinearLayout mLlInvoice1;
    @BindView(R.id.et_invoice_head)
    EditText mEtInvoiceHead;
    @BindView(R.id.et_invoice_code)
    EditText mEtInvoiceCode;
    @BindView(R.id.iv_invoice_help)
    ImageView mIvInvoiceHelp;
    @BindView(R.id.ll_invoice2)
    LinearLayout mLlInvoice2;
    @BindView(R.id.txt_invoice_sure)
    TextView mTxtInvoiceSure;
    private InvoiceBean bean;

    @Override
    public int bindLayout() {
        return R.layout.activity_invoice;
    }

    @Override
    public void initData() {
        initTitleBar("发票信息");
        setRightText("发票须知");
        mRgInvoice.setOnCheckedChangeListener(this);
        bean = new InvoiceBean();
        bean.setType(0);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String typeSecond = bundle.getString("typeSecond");
            String nameSecond = bundle.getString("nameSecond");
            String headSecond = bundle.getString("headSecond");
            String codeSecond = bundle.getString("codeSecond");
            MyLog.e("zzzSecond", typeSecond+","+nameSecond+","+headSecond+","+codeSecond);
            if (Const.STR0.equals(typeSecond)) {
                mRbInvoiceMyself.setChecked(true);
                mEtInvoiceMyself.setText(nameSecond);
            } else if (Const.STR1.equals(typeSecond)) {
                mRbInvoiceCompany.setChecked(true);
                mEtInvoiceHead.setText(headSecond);
                mEtInvoiceCode.setText(codeSecond);
            }
        }

        mEtInvoiceMyself.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bean.setName(editable.toString().trim());
            }
        });
        mEtInvoiceHead.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bean.setHead(editable.toString().trim());
            }
        });
        mEtInvoiceCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bean.setCode(editable.toString().trim());
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (mRbInvoiceMyself.isChecked()) {
            mLlInvoice1.setVisibility(View.VISIBLE);
            mLlInvoice2.setVisibility(View.GONE);
            //0代表个人
            bean.setType(0);
        } else {
            mLlInvoice2.setVisibility(View.VISIBLE);
            mLlInvoice1.setVisibility(View.GONE);
            //1代表单位
            bean.setType(1);
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_right, R.id.iv_invoice_help, R.id.txt_invoice_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                Intent intent = new Intent();
                intent.putExtra("invoiceType", -1);
                setResult(456, intent);
                finish();
                break;
            case R.id.tv_right:
                JumpIntent.jump(this, InvoiceInfoActivity.class);
                break;
            case R.id.iv_invoice_help:
                InvoiceHelpDialog dialog = new InvoiceHelpDialog(this);
                dialog.showDialog();
                break;
            case R.id.txt_invoice_sure:
                String name = mEtInvoiceMyself.getText().toString().trim();
                String head = mEtInvoiceHead.getText().toString().trim();
                String code = mEtInvoiceCode.getText().toString().trim();
                if (TextUtils.isEmpty(name) && bean.getType() == 0) {
                    ToastUtils.show("请输入个人名称");
                    return;
                }
                if (TextUtils.isEmpty(head) && bean.getType() == Const.INTEGER_1) {
                    ToastUtils.show("请输入抬头名称");
                    return;
                }
                if (TextUtils.isEmpty(code) && bean.getType() == Const.INTEGER_1) {
                    ToastUtils.show("请输入纳税人识别号");
                    return;
                }
                bean.setName(name);
                bean.setHead(head);
                bean.setCode(code);
                Intent intent2 = new Intent();
                intent2.putExtra("invoiceType", bean.getType());
                if (bean.getType() == 0) {
                    //个人
                    intent2.putExtra("invoiceName", bean.getName());
                } else if (bean.getType() == Const.INTEGER_1) {
                    //单位
                    intent2.putExtra("invoiceHead", bean.getHead());
                    intent2.putExtra("invoiceCode", bean.getCode());
                }
                setResult(456, intent2);
                finish();
                break;
            default:
                break;
        }
    }
}
