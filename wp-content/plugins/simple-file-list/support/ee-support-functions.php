<?php // Simple File List Script: ee-support-functions.php | Author: Mitchell Bennis | support@simplefilelist.com | Revised: 11.23.2019
	
if ( ! defined( 'ABSPATH' ) ) exit; // Exit if accessed directly
if ( ! wp_verify_nonce( $eeSFL_Nonce, 'eeInclude' ) ) exit('ERROR 98'); // Exit if nonce fails

$eeSFL_Log[] = 'Loaded: ee-support-functions';

if(!function_exists('eeProcessSupportPost')) {

	function eeProcessSupportPost($eeArray) {
		     
	    global $eeContact_From;
	    
	    $eeContact_Body = '';
	     
	    $eeIgnore = array('eeSupportForm', 'ee-support-form-nonce', '_wp_http_referer');
	    
	    if(is_array($eeArray)) {
	     
		    foreach ($eeArray as $eeKey => $eeValue) {
		        
		        if(!in_array($eeKey, $eeIgnore) AND $eeValue) {
						
					$eeValue = sanitize_text_field($eeValue);
					
					$eeKey = str_replace('eeContact_', '', $eeKey);
					
					$eeField = eeUnSlug($eeKey);
					
					$eeContact_Body .= $eeField . ': ' . $eeValue . PHP_EOL . PHP_EOL;
				}
		    }
		    
		    if(is_string($eeContact_Body)) {
			    
			    return $eeContact_Body;
		    
		    } else {
		    
		    	return FALSE; // Empty string...
		    	
		    }
	    
	    } else {
		    
		    return FALSE; // Bad POST array...
	    }
	}
}

if(!function_exists('eeMailError')) {
	
	function eeMailError($eeContact_wpError) {
		
		global $eeOutput, $eeContact_name, $eeContact_email, $eeContact_message, $eeContact_link;
		
		// Preserve input data
		$eeContact_name = sanitize_text_field(stripslashes(@$_POST['eeContact_name']));
		$eeContact_email = sanitize_email(@$_POST['eeContact_email']);
		$eeContact_message = sanitize_text_field(stripslashes(@$_POST['eeContact_message']));
		$eeContact_link = sanitize_text_field(@$_POST['eeContact_link']);
		
		$eeOutput .= '<div class="error"><p>' . __('The message failed to send.', 'ee-simple-file-list-pro') . '</p>';
		
		// $eeOutput .= "<pre>" . print_r($eeContact_wpError, TRUE) . "</pre></div>";
		
		return error_log(print_r($eeContact_wpError, true));
	}
}

if(!function_exists('eeUnSlug')) {
	
	function eeUnSlug($eeSlug) {
	   
	   $eeSlug = str_replace('eeContact_', '', $eeSlug);
	   $eeSlug = strtolower($eeSlug);
	   $eeSlug = str_replace('-', ' ', $eeSlug);
	   $eeSlug = str_replace('_', ' ', $eeSlug);
	   $eeSlug = ucwords($eeSlug);
	   return $eeSlug;
	} 
}
	
?>