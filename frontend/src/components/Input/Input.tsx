import React, { useState, forwardRef, type InputHTMLAttributes } from "react";

type InputType =
  | "text"
  | "password"
  | "email"
  | "number"
  | "search"
  | "tel"
  | "url"
  | "date"
  | "time"
  | "textarea";

interface InputProps extends Omit<InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement>, "type"> {
  label?: string;
  type?: InputType;
  error?: string;
  hint?: string;
  leftIcon?: React.ReactNode;
  rightIcon?: React.ReactNode;
  rows?: number; // for textarea
}

const EyeIcon = ({ open }: { open: boolean }) => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    {open ? (
      <>
        <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
        <circle cx="12" cy="12" r="3" />
      </>
    ) : (
      <>
        <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94" />
        <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19" />
        <line x1="1" y1="1" x2="23" y2="23" />
      </>
    )}
  </svg>
);

const Input = forwardRef<HTMLInputElement | HTMLTextAreaElement, InputProps>(
  (
    {
      label,
      type = "text",
      error,
      hint,
      leftIcon,
      rightIcon,
      rows = 4,
      className = "",
      disabled,
      ...props
    },
    ref
  ) => {
    const [showPassword, setShowPassword] = useState(false);

    const isPassword = type === "password";
    const isTextarea = type === "textarea";
    const resolvedType = isPassword ? (showPassword ? "text" : "password") : type;

    const baseWrapperStyle: React.CSSProperties = {
      display: "flex",
      flexDirection: "column",
      gap: "6px",
      width: "100%",
      fontFamily: "inherit",
    };

    const labelStyle: React.CSSProperties = {
      fontSize: "13px",
      fontWeight: 500,
      color: error ? "#b91c1c" : "#374151",
      letterSpacing: "0.01em",
    };

    const inputWrapperStyle: React.CSSProperties = {
      position: "relative",
      display: "flex",
      alignItems: isTextarea ? "flex-start" : "center",
    };

    const inputStyle: React.CSSProperties = {
      width: "100%",
      padding: isTextarea
        ? "10px 12px"
        : leftIcon
        ? "0 12px 0 38px"
        : "0 12px",
      paddingRight: isPassword || rightIcon ? "40px" : "12px",
      height: isTextarea ? "auto" : "40px",
      fontSize: "14px",
      color: "#111827",
      backgroundColor: disabled ? "#f9fafb" : "#fff",
      border: error
        ? "1.5px solid #f87171"
        : "1.5px solid #d1d5db",
      borderRadius: "8px",
      outline: "none",
      transition: "border-color 0.15s ease, box-shadow 0.15s ease",
      cursor: disabled ? "not-allowed" : "text",
      opacity: disabled ? 0.6 : 1,
      resize: isTextarea ? "vertical" : undefined,
      boxSizing: "border-box",
    };

    const iconLeftStyle: React.CSSProperties = {
      position: "absolute",
      left: "11px",
      top: isTextarea ? "11px" : "50%",
      transform: isTextarea ? "none" : "translateY(-50%)",
      color: "#9ca3af",
      display: "flex",
      pointerEvents: "none",
    };

    const iconRightStyle: React.CSSProperties = {
      position: "absolute",
      right: "10px",
      top: "50%",
      transform: "translateY(-50%)",
      color: "#9ca3af",
      display: "flex",
      cursor: isPassword ? "pointer" : "default",
      background: "none",
      border: "none",
      padding: "2px",
      borderRadius: "4px",
    };

    const hintStyle: React.CSSProperties = {
      fontSize: "12px",
      color: error ? "#ef4444" : "#6b7280",
      marginTop: "2px",
    };

    const handleFocus = (e: React.FocusEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      e.currentTarget.style.borderColor = error ? "#f87171" : "#6366f1";
      e.currentTarget.style.boxShadow = error
        ? "0 0 0 3px rgba(239,68,68,0.12)"
        : "0 0 0 3px rgba(99,102,241,0.12)";
    };

    const handleBlur = (e: React.FocusEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      e.currentTarget.style.borderColor = error ? "#f87171" : "#d1d5db";
      e.currentTarget.style.boxShadow = "none";
    };

    return (
      <div style={baseWrapperStyle} className={className}>
        {label && <label style={labelStyle}>{label}</label>}

        <div style={inputWrapperStyle}>
          {leftIcon && <span style={iconLeftStyle}>{leftIcon}</span>}

          {isTextarea ? (
            <textarea
              ref={ref as React.Ref<HTMLTextAreaElement>}
              rows={rows}
              style={inputStyle}
              disabled={disabled}
              onFocus={handleFocus}
              onBlur={handleBlur}
              {...(props as React.TextareaHTMLAttributes<HTMLTextAreaElement>)}
            />
          ) : (
            <input
              ref={ref as React.Ref<HTMLInputElement>}
              type={resolvedType}
              style={inputStyle}
              disabled={disabled}
              onFocus={handleFocus}
              onBlur={handleBlur}
              {...(props as React.InputHTMLAttributes<HTMLInputElement>)}
            />
          )}

          {isPassword && (
            <button
              type="button"
              style={iconRightStyle}
              onClick={() => setShowPassword((v) => !v)}
              tabIndex={-1}
              aria-label={showPassword ? "Hide password" : "Show password"}
            >
              <EyeIcon open={showPassword} />
            </button>
          )}

          {!isPassword && rightIcon && (
            <span style={{ ...iconRightStyle, cursor: "default" }}>{rightIcon}</span>
          )}
        </div>

        {(hint || error) && (
          <p style={hintStyle}>{error || hint}</p>
        )}
      </div>
    );
  }
);

Input.displayName = "Input";

export default Input;