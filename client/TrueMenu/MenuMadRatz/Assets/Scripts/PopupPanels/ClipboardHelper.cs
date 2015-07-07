using UnityEngine;
using System;
using System.Reflection;

public static class ClipboardHelper
{
	private static PropertyInfo m_systemCopyBufferProperty = null;
	private static PropertyInfo GetSystemCopyBufferProperty()
	{
		if (m_systemCopyBufferProperty == null)
		{
			Type T = typeof(GUIUtility);
			m_systemCopyBufferProperty = T.GetProperty("systemCopyBuffer", BindingFlags.Static | BindingFlags.NonPublic);
		}
		return m_systemCopyBufferProperty;
	}

	public static bool clipboardAvailable
	{
		get
		{
			return GetSystemCopyBufferProperty() != null;
		}
	}

	public static string clipboard
	{
		get 
		{
			PropertyInfo P = GetSystemCopyBufferProperty();
			return P != null ? (string)P.GetValue(null, null) : "";
		}
		set
		{
			PropertyInfo P = GetSystemCopyBufferProperty();
			if (P != null) P.SetValue(null, value, null);
		}
	}
}